package com.teja.fanout.ingestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class FileIngestor {

    private final BlockingQueue<String>[] queues;

    public FileIngestor(BlockingQueue<String>[] queues) {
        this.queues = queues;
    }

    public void readFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (BlockingQueue<String> q : queues) {
                    q.put(line);
                }
            }
            for (BlockingQueue<String> q : queues) q.put("EOF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
