package com.commerce.util.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends ClassicConverter {

    public static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public String convert(ILoggingEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (auth.getName().equalsIgnoreCase(ANONYMOUS_USER) ? "" : "USERNAME=" + auth.getName());
        }
        return "";
    }
}
