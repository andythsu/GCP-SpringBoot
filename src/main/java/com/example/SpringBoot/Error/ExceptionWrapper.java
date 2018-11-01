package com.example.SpringBoot.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Andy Su
 * @Date: 10/31/2018
 */
@ControllerAdvice
public class ExceptionWrapper extends ExceptionHandlerExceptionResolver {

    @ExceptionHandler(WebRequestException.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Throwable ex) {
        MessageKey msg = ((WebRequestException) ex).getMessageKey();
        return new ResponseEntity(msg, getStatus(msg.getStatus()));
    }

    public HttpStatus getStatus(int status){
        return HttpStatus.resolve(status);
    }
}
