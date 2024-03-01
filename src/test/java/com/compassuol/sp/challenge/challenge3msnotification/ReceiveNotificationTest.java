package com.compassuol.sp.challenge.challenge3msnotification;

import com.compassuol.sp.challenge.challenge3msnotification.domain.Notification;
import com.compassuol.sp.challenge.challenge3msnotification.domain.NotificationMessage;
import com.compassuol.sp.challenge.challenge3msnotification.domain.NotificationRepository;
import com.compassuol.sp.challenge.challenge3msnotification.domain.UserNotificationSend;
import com.compassuol.sp.challenge.challenge3msnotification.infra.mqueue.ReceiveNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Date;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReceiveNotificationTest {

    @InjectMocks
    private ReceiveNotification receiveNotification;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    public void testReceiveNotification() throws Exception {
        String payload = "{\"email\":\"test@example.com\",\"event\":\"CREATE\",\"sendDate\":\"2024-03-01T12:00:00\"}";


        doNothing().when(notificationRepository).save(any(Notification.class));


        receiveNotification.receiveNotification(payload);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(notificationCaptor.capture());

        Notification savedNotification = notificationCaptor.getValue();
        assertNotNull(savedNotification);
        assertEquals("test@example.com", savedNotification.getEmail());
        assertEquals(NotificationMessage.CREATE, savedNotification.getEvent());
        assertEquals("2024-03-01T12:00:00", savedNotification.getDate().toString());
    }

    @Test
    public void testReceiveNotificationWithError() throws Exception {

        String payloadWithError = "invalidPayload";
        doNothing().when(notificationRepository).save(any(Notification.class));
        receiveNotification.receiveNotification(payloadWithError);

        verify(receiveNotification, times(1)).receiveNotification(payloadWithError);
    }

    @Test
    public void testConstructorWithValidJson() throws JsonProcessingException {

        String validJson = "{\"email\":\"test@example.com\",\"event\":\"CREATE\",\"sendDate\":\"2024-03-01T12:00:00\"}";


        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        UserNotificationSend expectedUserNotificationSend = new UserNotificationSend("test@example.com", NotificationMessage.CREATE, new Date());
        Mockito.when(objectMapperMock.readValue(validJson, UserNotificationSend.class)).thenReturn(expectedUserNotificationSend);


        UserNotificationSend userNotificationSend = new UserNotificationSend();


        assertEquals("test@example.com", userNotificationSend.getEmail());
        assertEquals(NotificationMessage.CREATE, userNotificationSend.getEvent());
        assertEquals(new Date().toString(), userNotificationSend.getSendDate().toString());
    }

    @Test
    public void testConstructorWithInvalidJson() throws JsonProcessingException {

        String invalidJson = "invalidJson";


        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapperMock.readValue(invalidJson, UserNotificationSend.class)).thenThrow(JsonProcessingException.class);

        UserNotificationSend userNotificationSend = new UserNotificationSend();

        assertEquals(null, userNotificationSend.getEmail());
        assertEquals(null, userNotificationSend.getEvent());
        assertEquals(null, userNotificationSend.getSendDate());
    }
    @Test
    public void testGettersAndSetters() {

        Notification notification = new Notification();

        Long id = 1L;
        String email = "test@example.com";
        NotificationMessage event = NotificationMessage.CREATE;
        Date date = new Date();

        notification.setId(id);
        notification.setEmail(email);
        notification.setEvent(event);
        notification.setDate(date);

        assertEquals(id, notification.getId());
        assertEquals(email, notification.getEmail());
        assertEquals(event, notification.getEvent());
        assertEquals(date, notification.getDate());
    }

    @Test
    public void testEqualsAndHashCode() {

        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setEmail("test@example.com");
        notification1.setEvent(NotificationMessage.CREATE);
        notification1.setDate(new Date());

        Notification notification2 = new Notification();
        notification2.setId(1L);
        notification2.setEmail("test@example.com");
        notification2.setEvent(NotificationMessage.CREATE);
        notification2.setDate(new Date());

        assertEquals(notification1, notification2);
        assertEquals(notification1.hashCode(), notification2.hashCode());
    }

    @Test
    public void testToString() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setEmail("test@example.com");
        notification.setEvent(NotificationMessage.CREATE);
        notification.setDate(new Date());

        assertNotNull(notification.toString());
    }

    @Test
    public void testReceiveNotificationWithException() {
        try {
            String invalidPayload = "invalidPayload";

            Mockito.when(new UserNotificationSend(invalidPayload)).thenThrow(JsonProcessingException.class);

            receiveNotification.receiveNotification(invalidPayload);

            Mockito.verify(log, Mockito.times(1)).error(Mockito.anyString());

        } catch (Exception e) {

            fail("Exceção não esperada: " + e.getMessage());
        }
    }

}
