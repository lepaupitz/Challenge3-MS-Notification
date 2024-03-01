package com.compassuol.sp.challenge.challenge3msnotification.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationSend {

        private String email;
        private NotificationMessage event;
        private Date sendDate;

        // Adicione este construtor
        public UserNotificationSend(String json) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                UserNotificationSend userNotificationSend = mapper.readValue(json, UserNotificationSend.class);
                this.email = userNotificationSend.getEmail();
                this.event = userNotificationSend.getEvent();
                this.sendDate = userNotificationSend.getSendDate();
        }
}
