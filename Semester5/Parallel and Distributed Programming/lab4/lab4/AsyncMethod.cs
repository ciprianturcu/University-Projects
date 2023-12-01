using System.Net.Sockets;
using System.Text;

static class AsyncMethod
{

    public static void Run(List<string> hostnames)
    {
        var tasks = Enumerable.Range(0, hostnames.Count)
            .Select(i => Task.Factory.StartNew(() => StartAsyncClientAsync(hostnames[i], i)))
            .ToArray();
        Task.WaitAll(tasks);
    }

    private static async void StartAsyncClientAsync(string hostname, int id)
    {
        var socketHelper = SocketHelper.New(hostname, id);
        await ConnectAsync(socketHelper);

        await SendAsync(socketHelper, Parser.RequestString(socketHelper.HostName, socketHelper.Endpoint));

        await ReceiveAsync(socketHelper);

        Console.WriteLine($"Connection {id}: Content length: {Parser.ContentLength(socketHelper.Response.ToString())}");

        socketHelper.Socket.Shutdown(SocketShutdown.Both);
        socketHelper.Socket.Close();
    }

    private static async Task ConnectAsync(SocketHelper socketHelper)
    {
        socketHelper.Socket.BeginConnect(socketHelper.IPEndpoint, ConnectCallback, socketHelper);
        await Task.FromResult(socketHelper.ConnectDone.WaitOne());
    }

    private static void ConnectCallback(IAsyncResult result)
    {
        var socketHelper = result.AsyncState as SocketHelper;
        var socket = socketHelper.Socket;
        var id = socketHelper.ID;
        var hostname = socketHelper.HostName;

        socket.EndConnect(result);

        Console.WriteLine($"Connection {id}: Socket connected to {hostname} ({socket.RemoteEndPoint})");

        socketHelper.ConnectDone.Set();
    }

    private static async Task SendAsync(SocketHelper socketHelper, string data)
    {
        var bytes = Encoding.ASCII.GetBytes(data);

        socketHelper.Socket.BeginSend(bytes, 0, bytes.Length, 0, SendCallback, socketHelper);
        await Task.FromResult(socketHelper.SendDone.WaitOne());
    }

    private static void SendCallback(IAsyncResult result)
    {
        var socketHelper = result.AsyncState as SocketHelper;
        var socket = socketHelper.Socket;
        var id = socketHelper.ID;

        var sent = socket.EndSend(result);
        Console.WriteLine($"Connection {id}: Sent {sent} bytes to server.");
        socketHelper.SendDone.Set();
    }

    private static async Task ReceiveAsync(SocketHelper socketHelper)
    {
        socketHelper.Socket.BeginReceive(socketHelper.Buffer, 0, SocketHelper.BufferSize, 0, ReceiveCallback, socketHelper);
        await Task.FromResult(socketHelper.ReceiveDone.WaitOne());
    }

    private static void ReceiveCallback(IAsyncResult result)
    {
        var socketHelper = result.AsyncState as SocketHelper;
        var socket = socketHelper.Socket;

        try
        {
            var bytesReceived = socket.EndReceive(result);
            socketHelper.Response.Append(Encoding.ASCII.GetString(socketHelper.Buffer, 0, bytesReceived));
            if (!Parser.ReceivedFullResponse(socketHelper.Response.ToString()))
            {
                socket.BeginReceive(socketHelper.Buffer, 0, SocketHelper.BufferSize, 0, ReceiveCallback, socketHelper);
            }
            else
            {
                socketHelper.ReceiveDone.Set();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }
}