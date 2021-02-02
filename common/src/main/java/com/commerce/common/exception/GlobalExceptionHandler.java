package com.commerce.common.exception;

import com.commerce.common.dto.ResponseModel;
import com.commerce.common.exception.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,
            RolenameInvalidException.class,
            UserOrPasswordNotMatchException.class,
            EmailAddressAlreadyUseException.class,
            UsernameAlreadyTakenException.class,
            InvalidPasswordFormatException.class,
            ProductNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity customException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ResponseModel.error(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ResponseModel.error(status, "Invalid request body!"), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        StringBuilder details = new StringBuilder();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.append(",").append(error.getDefaultMessage());
        }
        return new ResponseEntity<>(ResponseModel.error(status,
                details.toString().length() > 0 ? details.toString().substring(1) : ""),
                status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationExceptions(ConstraintViolationException ex) {
        ex.printStackTrace();
        log.warn(StringUtils.trimAllWhitespace(getConstraintViolationExceptionMessage(ex)));
        return new ResponseEntity<>(ResponseModel.error(HttpStatus.BAD_REQUEST,
                getConstraintViolationExceptionMessage(ex)), HttpStatus.BAD_REQUEST);
    }

    private String getConstraintViolationExceptionMessage(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()).get(0);
    }
}
