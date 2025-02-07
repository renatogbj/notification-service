package modak.notificationservice.ratelimit;

import modak.notificationservice.ratelimit.algorithm.RateLimit;

import java.util.HashMap;
import java.util.Map;

public class RateLimitManager {

    private final Map<String, RateLimit> rateLimitByType = new HashMap<>();

    public void addRateLimit(final RateLimit rateLimit) {
        this.rateLimitByType.put(rateLimit.type(), rateLimit);
    }

    public boolean hasCapacity(final String type) {
        return rateLimitByType.containsKey(type) &&
                rateLimitByType.get(type).hasCapacity();
    }
}
