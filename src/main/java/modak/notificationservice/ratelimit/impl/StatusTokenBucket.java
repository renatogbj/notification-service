package modak.notificationservice.ratelimit.impl;

import modak.notificationservice.ratelimit.algorithm.TokenBucket;

import java.time.Duration;

public class StatusTokenBucket extends TokenBucket {

    @Override
    public String type() {
        return "update";
    }

    @Override
    protected int capacity() {
        return 2;
    }

    @Override
    public Duration interval() {
        return Duration.ofMinutes(1);
    }
}
