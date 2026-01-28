package com.teja.fanout.throttle;

public class SimpleRateLimiter {
    private final long intervalMs;
    private long lastTime = 0;

    public SimpleRateLimiter(int permitsPerSecond) {
        this.intervalMs = 1000L / permitsPerSecond;
    }

    public synchronized void acquire() throws InterruptedException {
        long now = System.currentTimeMillis();
        long wait = intervalMs - (now - lastTime);
        if (wait > 0) Thread.sleep(wait);
        lastTime = System.currentTimeMillis();
    }
}
