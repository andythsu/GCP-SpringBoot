package com.example.SpringBoot.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author: Andy Su
 * @Date: 10/31/2018
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(WebRequestException.class)
    protected ResponseEntity<Object> handleException(WebRequestException ex) {
        return buildResponseEntity(ex);
    }

    private ResponseEntity<Object> buildResponseEntity(Object ex) {
        return new ResponseEntity<>(ex, HttpStatus.resolve(((WebRequestException) ex).getStatus()));
    }
}