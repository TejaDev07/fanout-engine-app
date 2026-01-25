package com.teja.fanout.throttle;

public class SimpleRateLimiter {

    private final long intervalMillis;
    private long lastTime = 0;

    public SimpleRateLimiter(int permitsPerSecond) {
        this.intervalMillis = 1000L / permitsPerSecond;
    }

    public synchronized void acquire() {
        long now = System.currentTimeMillis();
        long waitTime = lastTime + intervalMillis - now;

        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        lastTime = System.currentTimeMillis();
    }
}
