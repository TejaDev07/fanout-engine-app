package com.teja.fanout.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueFactory {

    // ✅ This method MUST exist
    public static BlockingQueue<String> create(int size) {
        return new ArrayBlockingQueue<>(size);
    }
}
