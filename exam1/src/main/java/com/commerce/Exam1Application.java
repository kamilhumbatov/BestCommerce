package com.commerce;

import com.commerce.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Exam1Application {

    public static void main(String[] args) {
        SpringApplication.run(Exam1Application.class, args);
    }

}
