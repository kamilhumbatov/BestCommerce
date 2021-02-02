package com.commerce.services;

import com.commerce.dto.UserDto;

public interface SendMailService {
    void merchantRegistrMailSender(UserDto user);
}
