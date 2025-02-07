package modak.notificationservice.ratelimit.impl;

import modak.notificationservice.ratelimit.algorithm.TokenBucket;

import java.time.Duration;

public class NewsTokenBucket extends TokenBucket {

    @Override
    public String type() {
        return "news";
    }

    @Override
    protected int capacity() {
        return 1;
    }

    @Override
    protected Duration interval() {
        return Duration.ofDays(1);
    }
}
