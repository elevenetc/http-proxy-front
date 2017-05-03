package com.hello;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene.levenetc on 11/04/2017.
 */
class HttpClient extends ClientThread {

    private HttpProxy httpProxy;

    public HttpClient(String name, Socket client, HttpProxy httpProxy) {
        super(name, client);
        setDaemon(false);
        this.httpProxy = httpProxy;
    }

    @Override
    protected void run(Socket socket, BufferedReader in, BufferedWriter out) throws IOException {
        try {

            boolean end = true;


            out.write("HTTP/1.1 200 \r\n"); // Version & status code
            out.write("Content-Type: text/plain\r\n"); // The type of data
            out.write("Connection: close\r\n"); // Will close stream
            out.write("\r\n"); // End of headers

            String line;
            String query = null;
            Map<String, String> headers = new HashMap<>();
            HttpMethod httpMethod = null;
            while ((line = in.readLine()) != null) {
                if (line.length() == 0)
                    break;

                if (line.contains("POST")) {
                    httpMethod = HttpMethod.POST;
                    final String[] split = line.split(" ");
                    if (split.length >= 2) {
                        query = split[1];
                    }
                } else if (line.contains("GET")) {
                    httpMethod = HttpMethod.GET;
                    final String[] split = line.split(" ");
                    if (split.length >= 2) {
                        query = split[1];
                    }
                } else {
                    final String[] parts = line.split(":");
                    if (parts.length == 2) {
                        headers.put(parts[0], parts[1].trim());
                    }
                }

                //if()
                //Out.pln(line);
                //headers += line;
            }

            if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.GET) {
                //Out.pln(headers);
                //out.write("Thanks");
                //final String s = streamToString(inputStream);

                if (httpProxy != null) {
                    httpProxy.handle(httpMethod, query, headers, null, out);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
