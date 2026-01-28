package com.teja.fanout.sink;

import com.teja.fanout.metrics.Metrics;
import com.teja.fanout.throttle.SimpleRateLimiter;
import com.teja.fanout.transform.Transformer;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractSink implements Runnable {

    protected final BlockingQueue<String> queue;
    protected final Transformer<String> transformer;
    protected final SimpleRateLimiter limiter;

    public AbstractSink(BlockingQueue<String> q, Transformer<String> t, SimpleRateLimiter l) {
        this.queue = q;
        this.transformer = t;
        this.limiter = l;
    }

    protected abstract void send(String data);

    public void run() {
        try {
            while (true) {
                String record = queue.take();
                if ("EOF".equals(record)) break;

                limiter.acquire();
                int retries = 0;
                boolean success = false;

                while (retries < 3 && !success) {
                    try {
                        send(transformer.transform(record));
                        success = true;
                        Metrics.success.incrementAndGet();
                    } catch (Exception e) {
                        retries++;
                        if (retries == 3) Metrics.failed.incrementAndGet();
                    }
                }
                Metrics.processed.incrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
