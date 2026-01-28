package com.teja.fanout.transform;

public interface Transformer<T> {
    T transform(String input);
}
