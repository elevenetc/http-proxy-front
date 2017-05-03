package com.hello;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eugene.levenetc on 11/04/2017.
 */
public abstract class ClientThread extends Thread {

    private String name;
    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    BlockingQueue<String> inCommandsQueue;
    BlockingQueue<String> outCommandsQueue;
    private AtomicInteger connectionCounter;

    public ClientThread(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void setConnectionCounter(AtomicInteger connectionCounter) {

        this.connectionCounter = connectionCounter;
    }

    public void setInCommandsQueue(BlockingQueue<String> queue) {
        this.inCommandsQueue = queue;
    }

    public void setOutCommandsQueue(BlockingQueue<String> queue) {
        this.outCommandsQueue = queue;
    }

    @Override
    public void run() {
        try {
            onConnect();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            run(socket, in, out);
            Utils.close(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            onDisconnect();
        }
    }

    protected void onConnect() {
        Out.pln("client:" + name, "connected");
        if (connectionCounter != null) {
            connectionCounter.incrementAndGet();
        }
    }

    protected void onDisconnect() {
        Out.pln("client:" + name, "disconnected");
        if (connectionCounter != null) {
            connectionCounter.decrementAndGet();
        }
    }

    protected abstract void run(Socket socket, BufferedReader in, BufferedWriter out) throws IOException;
}
