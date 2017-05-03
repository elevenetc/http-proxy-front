package com.hello;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by eugene.levenetc on 10/04/2017.
 */
public class Utils {

    public static void offerSilent(BlockingQueue<String> queue, String value) {
        if (value == null) return;
        queue.offer(value);
    }

    public static String takeSilent(BlockingQueue<String> queue) {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> queryToMap(String query) {
        final String[] split1 = query.split("\\?");
        final String url = split1[0];
        query = split1[1].replace("/?", "");
        final String[] split = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String pair : split) {
            final String[] p = pair.split("=");
            map.put(p[0], p[1]);
        }
        map.put("url", url);
        return map;
    }

    public static void sleep() {
        sleep(Long.MAX_VALUE);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void close(ClientThread clientThread) {
        close(clientThread.socket, clientThread.in, clientThread.out);
    }

    public static void close(Socket socket, BufferedReader in, BufferedWriter out) {

        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeSilently(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String streamToString(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    public static void createSocketServer(int port) throws IOException {

        Socket socket = null;
        boolean end = true;

        ServerSocket listener = new ServerSocket(port);
        while (end) {
            socket = listener.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(new Date().toString());
        }

        socket.close();
        listener.close();
    }
}
