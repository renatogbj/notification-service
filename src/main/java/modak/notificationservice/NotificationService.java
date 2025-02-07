package modak.notificationservice;

public interface NotificationService {

    void send(String type, String userId, String message);
}