package com.compassuol.sp.challenge.challenge3msnotification.infra.mqueue;

import com.compassuol.sp.challenge.challenge3msnotification.domain.Notification;
import com.compassuol.sp.challenge.challenge3msnotification.domain.NotificationRepository;
import com.compassuol.sp.challenge.challenge3msnotification.domain.UserNotificationSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiveNotification {


    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = {"${mq.queues.user}"})
    public void receiveNotification(@Payload String payload) {
        try {
            UserNotificationSend notificationRecieve = new UserNotificationSend(payload);

            Notification notification = new Notification();
            notification.setDate(notificationRecieve.getSendDate());
            notification.setEvent(notificationRecieve.getEvent());
            notification.setEmail(notificationRecieve.getEmail());

            notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Erro ao receber solicitacao de notificacao: {} ", e.getMessage());
        }
    }


}
