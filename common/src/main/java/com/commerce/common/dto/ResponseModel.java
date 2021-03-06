package com.commerce.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ResponseModel<T> {

    private int status;

    private String message;

    private Date timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ResponseModel ok(T result) {
        return new ResponseModel(HttpStatus.OK.value(), "", new Date(), result);
    }

    public static <T> ResponseModel error(HttpStatus httpStatus, String message) {
        return new ResponseModel(httpStatus.value(), message, new Date(), null);
    }

    public static <T> ResponseModel unauthorized(String message) {
        return new ResponseModel(HttpStatus.UNAUTHORIZED.value(), message, new Date(), null);
    }
}
