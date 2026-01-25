package com.teja.fanout.ingestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class FileIngestor {

    private final BlockingQueue<String>[] queues;

    @SafeVarargs
    public FileIngestor(BlockingQueue<String>... queues) {
        this.queues = queues;
    }

    public void readFile(String filePath) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                count++;

                for (BlockingQueue<String> q : queues) {
                    q.put(line);   // Fan-out
                }

                System.out.println("Line " + count + " queued for all sinks: " + line);
            }

            // Send EOF to all sinks
            for (BlockingQueue<String> q : queues) {
                q.put("EOF");
            }

            System.out.println("✅ File ingestion completed. Total lines: " + count);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
