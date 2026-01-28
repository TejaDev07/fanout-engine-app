package com.teja.fanout.config;

import com.google.gson.Gson;
import java.io.FileReader;

public class ConfigLoader {
    public static AppConfig load() {
        try {
            return new Gson().fromJson(new FileReader("config.json"), AppConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }
}
