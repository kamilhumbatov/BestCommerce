package com.commerce.send;

import com.commerce.services.SendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisterSendToKafka {

    private final SendMailService sendMailService;

//    @KafkaListener(id = "1",
//            topics = "user-management-reset-password",
//            groupId = "notification-group-id",
//            containerFactory = "kafkaJsonListenerContainerFactory")
    public void resetPasswordListener() {
        //log.info("Message received : {}", dto);
        //sendMailService.passwordResetMailSender(dto);
    }

}
