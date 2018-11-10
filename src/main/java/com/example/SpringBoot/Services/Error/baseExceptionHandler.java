package com.example.SpringBoot.Services.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * @author: Andy Su
 * @Date: 10/31/2018
 */
public abstract class baseExceptionHandler extends ExceptionHandlerExceptionResolver {

    @ExceptionHandler(WebRequestException.class)
    public ResponseEntity<?> handleException(WebRequestException ex) {
        MessageKey msg = ex.getMessageKey();
        return new ResponseEntity(msg, getStatus(msg.getStatus()));
    }

    public HttpStatus getStatus(int status){
        return HttpStatus.resolve(status);
    }
}
