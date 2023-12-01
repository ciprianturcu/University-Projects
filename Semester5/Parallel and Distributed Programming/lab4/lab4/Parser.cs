static class Parser
{
    public const int port = 80;

    public static string RequestString(string hostname, string endpoint) =>
       $"GET {endpoint} HTTP/1.1\r\n" +
        $"Host: {hostname}\r\n" +
            $"Content-Length: 0\r\n\r\n";

    public static int ContentLength(string content)
    {
        foreach (string responseLine in content.Split('\r', '\n'))
        {
            var headDetails = responseLine.Split(':');

            if (headDetails[0] == "Content-Length")
                return int.Parse(headDetails[1]);
        }

        throw new Exception("No Content-Length header received");
    }

    public static bool ReceivedFullResponse(string content) =>
      content.Contains("\r\n\r\n");
}