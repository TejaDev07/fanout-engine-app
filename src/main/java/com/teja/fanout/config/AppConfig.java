package com.teja.fanout.config;

import java.util.Map;

public class AppConfig {
    public String filePath;
    public int queueSize;
    public Map<String, Integer> rateLimits;
}
