package com.commerce.exception;

import com.commerce.dto.ResponseModel;
import com.commerce.exception.models.EmailAddressAlreadyUseException;
import com.commerce.exception.models.RolenameInvalidException;
import com.commerce.exception.models.UsernameAlreadyTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,
            RolenameInvalidException.class,
            EmailAddressAlreadyUseException.class,
            UsernameAlreadyTakenException.class
    })
    public ResponseEntity customException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ResponseModel.error(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(ResponseModel.error(status, "Request body is missing"), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ResponseModel.error(status, "Method argument not valid"), status);
    }
}
