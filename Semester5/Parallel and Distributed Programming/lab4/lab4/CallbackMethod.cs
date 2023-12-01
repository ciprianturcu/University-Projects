using System.Net.Sockets;
using System.Text;

static class CallbackMethod
{

    public static void Run(List<string> hostnames)
    {

        CountdownEvent countdownEvent = new(hostnames.Count);

        for (int index = 0; index < hostnames.Count; index++)
        {
            StartClient(hostnames[index], index, countdownEvent);
        }

        countdownEvent.Wait();

    }

    private static void StartClient(string host, int id, CountdownEvent countdownEvent)
    {
        var socketHelper = SocketHelper.New(host, id, countdownEvent);
        socketHelper.Socket.BeginConnect(socketHelper.IPEndpoint, Connected, socketHelper);

    }

    private static void Connected(IAsyncResult result)
    {
        var socketHelper = result.AsyncState as SocketHelper;
        var socket = socketHelper.Socket;
        var id = socketHelper.ID;
        var hostname = socketHelper.HostName;

        socket.EndConnect(result);
        Console.WriteLine($"Connection {id}: Socket connected to {hostname} ({socket.RemoteEndPoint})");

        var data = Encoding.ASCII.GetBytes(Parser.RequestString(socketHelper.HostName, socketHelper.Endpoint));
        socket.BeginSend(data, 0, data.Length, 0, Sent, socketHelper);
    }

    private static void Sent(IAsyncResult result)
    {
        var socketHelper = result.AsyncState as SocketHelper;
        var socket = socketHelper.Socket;
        var id = socketHelper.ID;

        var sent = socket.EndSend(result);
        Console.WriteLine($"Connection {id}: Sent {sent} bytes to server.");

        socket.BeginReceive(socketHelper.Buffer, 0, SocketHelper.BufferSize, 0, Receiving, socketHelper);
    }

    private static void Receiving(IAsyncResult result)
    {
        var socketHelper = result.AsyncState as SocketHelper;
        var socket = socketHelper.Socket;

        try
        {

            var bytesRead = socket.EndReceive(result);
            socketHelper.Response.Append(Encoding.ASCII.GetString(socketHelper.Buffer, 0, bytesRead));

            if (!Parser.ReceivedFullResponse(socketHelper.Response.ToString()))
            {
                socket.BeginReceive(socketHelper.Buffer, 0, SocketHelper.BufferSize, 0, Receiving, socketHelper);
            }
            else
            {
                Console.WriteLine($"Connection {socketHelper.ID}: Content length: {Parser.ContentLength(socketHelper.Response.ToString())}");

                socket.Shutdown(SocketShutdown.Both);
                socket.Close();

                File.WriteAllText($"ResultFiles/{socketHelper.HostName}.html", socketHelper.Response.ToString());
                socketHelper.CDE.Signal();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }

    }
}