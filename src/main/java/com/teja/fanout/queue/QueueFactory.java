package com.teja.fanout.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueFactory {

    public static BlockingQueue<String> createQueue(int size) {
        return new ArrayBlockingQueue<>(size);
    }
}
