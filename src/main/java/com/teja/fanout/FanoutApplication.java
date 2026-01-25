package com.teja.fanout;

import com.teja.fanout.ingestion.FileIngestor;
import com.teja.fanout.queue.QueueFactory;
import com.teja.fanout.sink.RestApiSink;
import com.teja.fanout.sink.MessageQueueSink;
import com.teja.fanout.transform.JsonTransformer;
import com.teja.fanout.throttle.SimpleRateLimiter;
import com.teja.fanout.metrics.Metrics;

import java.util.concurrent.*;

public class FanoutApplication {

    public static void main(String[] args) {

        System.out.println("🚀 Fan-out Engine Started...");

        BlockingQueue<String> restQueue = QueueFactory.createQueue(1000);
        BlockingQueue<String> mqQueue = QueueFactory.createQueue(1000);

        SimpleRateLimiter restLimiter = new SimpleRateLimiter(2);
        SimpleRateLimiter mqLimiter = new SimpleRateLimiter(1);

        FileIngestor ingestor = new FileIngestor(restQueue, mqQueue);

        RestApiSink restSink =
                new RestApiSink(restQueue, new JsonTransformer(), restLimiter);

        MessageQueueSink mqSink =
                new MessageQueueSink(mqQueue, new JsonTransformer(), mqLimiter);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> ingestor.readFile("sample.txt"));
        executor.submit(restSink);
        executor.submit(mqSink);

        executor.shutdown();
    }
}
