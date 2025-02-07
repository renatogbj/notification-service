package modak.notificationservice;

import modak.notificationservice.ratelimit.RateLimitManager;

public class NotificationServiceImpl implements NotificationService {

    private final Gateway gateway;
    private final RateLimitManager rateLimitManager;

    public NotificationServiceImpl(final Gateway gateway, final RateLimitManager rateLimitManager) {
        this.gateway = gateway;
        this.rateLimitManager = rateLimitManager;
    }

    @Override
    public void send(final String type, final String userId, final String message) {
        if (this.rateLimitManager.hasCapacity(type)) {
            this.gateway.send(userId, message);
        }
    }

}