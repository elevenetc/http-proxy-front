package com.hello;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.hello.Utils.offerSilent;
import static com.hello.Utils.takeSilent;

/**
 * Created by eugene.levenetc on 18/04/2017.
 */
public class BidirectionalConnection {

    LinkedBlockingQueue<String> left = new LinkedBlockingQueue<>();
    LinkedBlockingQueue<String> right = new LinkedBlockingQueue<>();

    public BidirectionalConnection() {

        new Thread(() -> {
            while (true) {
                offerSilent(left, takeSilent(right));
            }
        }).start();

        new Thread(() -> {
            while (true) {
                offerSilent(right, takeSilent(left));
            }
        }).start();
    }

    public void addLeft(String value) {
        left.offer(value);
    }

    public void addRight(String value) {
        right.offer(value);
    }

    public BlockingQueue<String> getLeft() {
        return left;
    }

    public BlockingQueue<String> getRight() {
        return right;
    }
}
