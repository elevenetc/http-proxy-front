package com.hello;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by eugene.levenetc on 11/04/2017.
 */
public class TcpClient extends ClientThread {

    private volatile boolean connected = true;

    public TcpClient(String name, Socket socket) {
        super(name, socket);
    }

    @Override
    protected void run(Socket socket, BufferedReader in, BufferedWriter out) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (connected) {
                    try {
                        final String result = in.readLine();
                        final Command command = JsonEncoder.toObject(result, Command.class);
                        if (command == null) {
                            connected = false;
                        }
                        outCommandsQueue.offer(result);
                        Out.pln("RECEIVED:", command);
                    } catch (IOException e) {
                        e.printStackTrace();
                        connected = false;
                    }
                }
            }
        }).start();
        while (connected) {
            try {

                while (connected) {
                    final Command cmd = JsonEncoder.toObject(inCommandsQueue.take(), Command.class);

                    Out.pln("calling laptop");
                    out.write(JsonEncoder.toString(cmd) + "\n");//writing
                    Out.pln("reading response");//in
                    out.flush();
                    Out.pln("sending back result");


                    //outCommandsQueue.offer("{result:}" + (cmd.data + cmd.data));//getting
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
                connected = false;
            }
        }
    }
}