package com.commerce.services;

import com.commerce.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaReciever {

    @KafkaListener(topics = "${kafka.topic.name}")
    public void recieveData(Student student) {
        log.info("Data - " + student.toString() + " recieved");
    }
}