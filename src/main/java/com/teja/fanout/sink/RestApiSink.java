package com.teja.fanout.sink;

import com.teja.fanout.transform.Transformer;
import com.teja.fanout.throttle.SimpleRateLimiter;
import com.teja.fanout.metrics.Metrics;

import java.util.concurrent.BlockingQueue;

public class RestApiSink implements Runnable {

    private final BlockingQueue<String> queue;
    private final Transformer<String> transformer;
    private final SimpleRateLimiter rateLimiter;

    public RestApiSink(BlockingQueue<String> queue,
                       Transformer<String> transformer,
                       SimpleRateLimiter rateLimiter) {
        this.queue = queue;
        this.transformer = transformer;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String record = queue.take();
                if ("EOF".equals(record)) break;

                rateLimiter.acquire();

                String transformed = transformer.transform(record);
                System.out.println("REST Sink received: " + transformed);

                Metrics.restSuccess.incrementAndGet();
                Metrics.totalProcessed.incrementAndGet();
            }

            System.out.println("✅ REST Sink finished processing all records.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
