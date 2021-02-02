package com.commerce.services;

import com.commerce.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaReciever {

    private final SendMailService sendMailService;

    @KafkaListener(id = "1", topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void recieveData(UserDto user) {
        sendMailService.merchantRegistrMailSender(user);
        log.info("Data - " + user.getEmail() + " recieved");
    }
}