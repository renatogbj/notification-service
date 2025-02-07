package modak.notificationservice;

import modak.notificationservice.ratelimit.RateLimitManager;
import modak.notificationservice.ratelimit.impl.MarketingTokenBucket;
import modak.notificationservice.ratelimit.impl.NewsTokenBucket;
import modak.notificationservice.ratelimit.impl.StatusTokenBucket;

public class Solution {



    public static void main(String[] args) {

        var rateLimitManager = initRateLimits();

        NotificationServiceImpl service = new NotificationServiceImpl(new Gateway(), rateLimitManager);

        service.send("news", "user", "news 1");

        service.send("news", "user", "news 2");

        service.send("news", "user", "news 3");

        service.send("news", "another user", "news 1");

        service.send("update", "user", "update 1");

    }

    private static RateLimitManager initRateLimits() {
        var rateLimitDelegator = new RateLimitManager();
        rateLimitDelegator.addRateLimit(new NewsTokenBucket());
        rateLimitDelegator.addRateLimit(new MarketingTokenBucket());
        rateLimitDelegator.addRateLimit(new StatusTokenBucket());
        return rateLimitDelegator;
    }

}