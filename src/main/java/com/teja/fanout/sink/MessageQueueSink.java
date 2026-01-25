package com.teja.fanout.sink;

import com.teja.fanout.transform.Transformer;
import com.teja.fanout.throttle.SimpleRateLimiter;
import com.teja.fanout.metrics.Metrics;

import java.util.concurrent.BlockingQueue;

public class MessageQueueSink implements Runnable {

    private final BlockingQueue<String> queue;
    private final Transformer<String> transformer;
    private final SimpleRateLimiter rateLimiter;

    public MessageQueueSink(BlockingQueue<String> queue,
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
                System.out.println("MQ Sink published: " + transformed);

                Metrics.mqSuccess.incrementAndGet();
                Metrics.totalProcessed.incrementAndGet();
            }

            System.out.println("✅ MQ Sink finished processing all records.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
