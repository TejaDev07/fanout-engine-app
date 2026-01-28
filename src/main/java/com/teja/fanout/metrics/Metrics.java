package com.teja.fanout.metrics;

import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
    public static AtomicLong processed = new AtomicLong();
    public static AtomicLong success = new AtomicLong();
    public static AtomicLong failed = new AtomicLong();

    public static void startReporter() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    long p = processed.get();
                    System.out.println("📊 Metrics | Processed=" + p +
                            " Success=" + success.get() +
                            " Failed=" + failed.get());
                }
            } catch (InterruptedException ignored) {}
        }).start();
    }
}
