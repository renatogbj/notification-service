package modak.notificationservice.ratelimit.algorithm;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;

import java.time.Duration;

public abstract class TokenBucket implements RateLimit {

    private final LocalBucket bucket;

    public TokenBucket() {
        var refill = Refill.intervally(capacity(), interval());
        var limit = Bandwidth.classic(capacity(), refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public boolean hasCapacity() {
        return bucket.tryConsume(1);
    }

    protected abstract int capacity();

    protected abstract Duration interval();
}
