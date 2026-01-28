package com.teja.fanout.transform;

public class ProtoTransformer implements Transformer<String> {
    public String transform(String input) {
        return "PROTO[" + input + "]";
    }
}
