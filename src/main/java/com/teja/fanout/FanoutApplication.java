package com.teja.fanout;

import com.teja.fanout.config.*;
import com.teja.fanout.ingestion.FileIngestor;
import com.teja.fanout.metrics.Metrics;
import com.teja.fanout.queue.QueueFactory;
import com.teja.fanout.sink.*;
import com.teja.fanout.throttle.SimpleRateLimiter;
import com.teja.fanout.transform.*;

import java.util.concurrent.*;

public class FanoutApplication {

    public static void main(String[] args) {

        AppConfig config = ConfigLoader.load();
        Metrics.startReporter();

        BlockingQueue<String> restQ = QueueFactory.create(config.queueSize);
        BlockingQueue<String> grpcQ = QueueFactory.create(config.queueSize);
        BlockingQueue<String> mqQ   = QueueFactory.create(config.queueSize);
        BlockingQueue<String> dbQ   = QueueFactory.create(config.queueSize);

        FileIngestor ingestor = new FileIngestor(
                new BlockingQueue[]{restQ, grpcQ, mqQ, dbQ});

        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.submit(() -> ingestor.readFile(config.filePath));
        executor.submit(new RestApiSink(restQ, new JsonTransformer(),
                new SimpleRateLimiter(config.rateLimits.get("rest"))));
        executor.submit(new GrpcSink(grpcQ, new ProtoTransformer(),
                new SimpleRateLimiter(config.rateLimits.get("grpc"))));
        executor.submit(new MessageQueueSink(mqQ, new XmlTransformer(),
                new SimpleRateLimiter(config.rateLimits.get("mq"))));
        executor.submit(new WideColumnDbSink(dbQ, new JsonTransformer(),
                new SimpleRateLimiter(config.rateLimits.get("db"))));

// Optional graceful shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));

    }
}
