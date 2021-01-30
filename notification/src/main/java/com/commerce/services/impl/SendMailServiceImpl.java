package com.commerce.services.impl;

import com.commerce.services.SendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    @Value("${spring.mail.username}")
    private String hostMail;

    private final JavaMailSender javaMailSender;

    @Override
    public void merchantRegistrMailSender() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(hostMail);
        message.setTo("");
        message.setSubject("SUBJECT");
        message.setText("");
        javaMailSender.send(message);
        log.info("Mail sent to {}", "");
    }
}
