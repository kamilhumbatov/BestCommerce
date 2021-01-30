package com.commerce.services;

import com.commerce.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public void sendData(Student student) {
        // TODO Auto-generated method stub
        Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaHeaders.TOPIC, topicName);
        kafkaTemplate.send(new GenericMessage<Student>(student, headers));
        // use the below to send String values through kafka
        // kafkaTemplate.send(topicName, "some string value")
        log.info("Data - " + student.toString() + " sent to Kafka Topic - " + topicName);
    }
}