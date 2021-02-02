package com.commerce.util.log;

import ch.qos.logback.classic.PatternLayout;
import org.springframework.stereotype.Component;

@Component
public class PatternLayoutWithUserContext extends PatternLayout {

    static {
        PatternLayout.defaultConverterMap.put(
                "sid", UserConverter.class.getName());
    }
}