package modak.notificationservice;

import modak.notificationservice.ratelimit.RateLimitManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private Gateway gateway;

    @Mock
    private RateLimitManager rateLimitManager;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void shouldSendSingleNotification() {
        // given
        var userId = "userId";
        var message = "message";
        var type = "type";
        when(rateLimitManager.hasCapacity(type)).thenReturn(true);

        // when
        this.notificationService.send(type, userId, message);

        // then
        verify(this.gateway).send(userId, message);
    }

    @Test
    void shouldNotSendNotificationsWhenCapacityIsExceeded() {
        // given
        var userId = "userId";
        var message = "message";
        var type = "type";
        when(rateLimitManager.hasCapacity(type)).thenReturn(false);

        // when
        this.notificationService.send(type, userId, message);

        // then
        verify(this.gateway, never()).send(userId, message);
    }

    @Test
    void shouldSendTwoOutOfThreeNotificationsWhenCapacityIsExceededTemporarily() {
        // given
        var userId = "userId";
        var message = "message";
        var type = "type";
        when(rateLimitManager.hasCapacity(type)).thenReturn(true, false, true);

        // when
        this.notificationService.send(type, userId, message);
        this.notificationService.send(type, userId, message);
        this.notificationService.send(type, userId, message);

        // then
        verify(this.gateway, times(2)).send(userId, message);
    }

}
