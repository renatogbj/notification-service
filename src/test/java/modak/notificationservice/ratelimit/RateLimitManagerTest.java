package modak.notificationservice.ratelimit;

import modak.notificationservice.ratelimit.impl.MarketingTokenBucket;
import modak.notificationservice.ratelimit.impl.NewsTokenBucket;
import modak.notificationservice.ratelimit.impl.StatusTokenBucket;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateLimitManagerTest {

    private final RateLimitManager rateLimitManager = new RateLimitManager();

    @Test
    void shouldNotHaveCapacityForStatusNotificationsAfterBandwidthExceeded() {
        // given
        var rateLimit = new StatusTokenBucket();
        rateLimitManager.addRateLimit(rateLimit);

        // when
        boolean result1 = rateLimitManager.hasCapacity(rateLimit.type());
        boolean result2 = rateLimitManager.hasCapacity(rateLimit.type());
        boolean result3 = rateLimitManager.hasCapacity(rateLimit.type());

        // then
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    void shouldNotHaveCapacityForNewsNotificationsAfterBandwidthExceeded() {
        // given
        var rateLimit = new NewsTokenBucket();
        rateLimitManager.addRateLimit(rateLimit);

        // when
        boolean result1 = rateLimitManager.hasCapacity(rateLimit.type());
        boolean result2 = rateLimitManager.hasCapacity(rateLimit.type());

        // then
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void shouldNotHaveCapacityForMarketingNotificationsAfterBandwidthExceeded() {
        // given
        var rateLimit = new MarketingTokenBucket();
        rateLimitManager.addRateLimit(rateLimit);

        // when
        boolean result1 = rateLimitManager.hasCapacity(rateLimit.type());
        boolean result2 = rateLimitManager.hasCapacity(rateLimit.type());
        boolean result3 = rateLimitManager.hasCapacity(rateLimit.type());
        boolean result4 = rateLimitManager.hasCapacity(rateLimit.type());

        // then
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }

    @Test
    void shouldHaveCapacityWhenBandwidthIsRefilledAfterMaxInterval() throws InterruptedException {
        // given
        var statusRateLimit = new StatusTokenBucket() {
            @Override
            public Duration interval() {
                return Duration.ofMillis(200);
            }
        };
        rateLimitManager.addRateLimit(statusRateLimit);

        // when
        boolean result1 = rateLimitManager.hasCapacity(statusRateLimit.type());
        boolean result2 = rateLimitManager.hasCapacity(statusRateLimit.type());
        boolean result3 = rateLimitManager.hasCapacity(statusRateLimit.type());
        TimeUnit.MILLISECONDS.sleep(201);
        boolean result4 = rateLimitManager.hasCapacity(statusRateLimit.type());

        // then
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertTrue(result4);
    }

    @Test
    void shouldNotHaveCapacityWhenNotificationTypeIsUnknown() {
        // when
        boolean result = rateLimitManager.hasCapacity("random notification type");

        // then
        assertFalse(result);
    }

}
