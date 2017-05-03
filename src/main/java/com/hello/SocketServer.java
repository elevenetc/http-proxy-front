package com.hello;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by eugene.levenetc on 10/04/2017.
 */
public class SocketServer {

    private String name;
    private int port;
    private volatile boolean started;
    private ClientThreadFactory clientFactory;

    public SocketServer(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public void setClientFactory(ClientThreadFactory clientFactory) {

        this.clientFactory = clientFactory;
    }

    public void stop() {

        started = false;
    }

    public void start() {

        if (started) {
            return;
        }

        new Thread(SocketServer.this::run).start();
    }

    private void run() {
        started = true;

        Out.tag(this, "started:" + name);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            while (started) {
                clientFactory.createClientThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Utils.closeSilently(serverSocket);

        Out.tag(this, "finished");

        started = false;
    }


}
