package com.commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String CODE_404 = "The resource you were trying to reach is not found";
    private static final String CODE_403 = "Accessing the resource you were trying to reach is forbidden!";
    private static final String CODE_401 = "Full authentication is required to access this resource";
    private static final String CODE_400 = "Front error!";
    private static final String CODE_200 = "Successfully retrieved";

    @Bean
    public Docket online() {
        return create("Best Commerce", "com.commerce.controller");
    }

    public Docket create(String name, String pack) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(name)
                .select().apis(RequestHandlerSelectors.basePackage(pack))
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getResponseMessage())
                .globalResponseMessage(RequestMethod.POST, getResponseMessage())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Best Commerce Services",
                "Best Commerce System",
                "1.0.0",
                "",
                new Contact("", "", ""),
                "", "", Collections.emptyList());
    }

    private List<ResponseMessage> getResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(404).message(CODE_404).build(),
                new ResponseMessageBuilder().code(403).message(CODE_403).build(),
                new ResponseMessageBuilder().code(401).message(CODE_401).build(),
                new ResponseMessageBuilder().code(400).message(CODE_400).build(),
                new ResponseMessageBuilder().code(200).message(CODE_200).build()
        );
    }
}