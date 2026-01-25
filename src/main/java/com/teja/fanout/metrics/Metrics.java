package com.teja.fanout.metrics;

import java.util.concurrent.atomic.AtomicLong;

public class Metrics {

    public static AtomicLong totalProcessed = new AtomicLong(0);

    public static AtomicLong restSuccess = new AtomicLong(0);
    public static AtomicLong restFailure = new AtomicLong(0);

    public static AtomicLong mqSuccess = new AtomicLong(0);
    public static AtomicLong mqFailure = new AtomicLong(0);
}
