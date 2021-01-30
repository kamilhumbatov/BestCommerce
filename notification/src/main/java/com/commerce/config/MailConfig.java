package com.commerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private static final String TRANSPORT_PROTOCOL_CONFIG = "mail.transport.protocol";
    private static final String SMTP_AUTH_CONFIG = "mail.smtp.auth";
    private static final String SMTP_STARTTLS_CONFIG = "mail.smtp.starttls.enable";
    private static final String SMTP_SSL_TRUST_CONFIG = "mail.smtp.ssl.trust";

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTls;


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put(TRANSPORT_PROTOCOL_CONFIG, protocol);
        props.put(SMTP_AUTH_CONFIG, auth);
        props.put(SMTP_STARTTLS_CONFIG, startTls);
        props.put(SMTP_SSL_TRUST_CONFIG, host);

        return mailSender;
    }
}
