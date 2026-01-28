package com.teja.fanout.transform;

public class XmlTransformer implements Transformer<String> {
    public String transform(String input) {
        return "<record>" + input + "</record>";
    }
}
