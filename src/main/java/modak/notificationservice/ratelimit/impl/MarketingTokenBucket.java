package modak.notificationservice.ratelimit.impl;

import modak.notificationservice.ratelimit.algorithm.TokenBucket;

import java.time.Duration;

public class MarketingTokenBucket extends TokenBucket {

    @Override
    public String type() {
        return "marketing";
    }

    @Override
    protected int capacity() {
        return 3;
    }

    @Override
    protected Duration interval() {
        return Duration.ofHours(1);
    }
}
