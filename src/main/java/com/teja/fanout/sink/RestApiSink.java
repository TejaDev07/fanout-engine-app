package com.teja.fanout.sink;

public class RestApiSink extends AbstractSink {
    public RestApiSink(java.util.concurrent.BlockingQueue<String> q,
                       com.teja.fanout.transform.Transformer<String> t,
                       com.teja.fanout.throttle.SimpleRateLimiter l) {
        super(q, t, l);
    }

    protected void send(String data) {
        System.out.println("🌐 REST -> " + data);
    }
}
