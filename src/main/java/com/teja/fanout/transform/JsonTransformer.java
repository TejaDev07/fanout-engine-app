package com.teja.fanout.transform;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonTransformer implements Transformer<String> {

    private final Gson gson = new Gson();

    @Override
    public String transform(String record) {
        JsonObject json = gson.fromJson(record, JsonObject.class);
        return gson.toJson(json);
    }
}
