package com.hello;

import java.net.Socket;

/**
 * Created by eugene.levenetc on 11/04/2017.
 */
public interface ClientThreadFactory {
    Thread createClientThread(Socket socket);
}
