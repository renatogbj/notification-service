package modak.notificationservice.ratelimit.algorithm;

public interface RateLimit {

    boolean hasCapacity();

    String type();

}
