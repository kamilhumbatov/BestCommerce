package com.commerce.services;

import com.commerce.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaReciever {

    @KafkaListener(id = "1", topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
    public void recieveData(Student student) {
        log.info("Data - " + student.toString() + " recieved");
    }
}