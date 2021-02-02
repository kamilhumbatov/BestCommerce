package com.commerce.services.impl;

import com.commerce.config.MailConfig;
import com.commerce.dto.UserDto;
import com.commerce.services.SendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    @Value("${spring.mail.username}")
    private String hostMail;

    private final MailConfig mailConfig;

    @Override
    public void merchantRegistrMailSender(UserDto user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(hostMail);
        message.setTo(user.getEmail());
        message.setSubject("You have successfully registered");
        message.setText("Thank for registration");
        mailConfig.getJavaMailSender().send(message);
        log.info("Mail sent to {}", user.getEmail());
    }
}
