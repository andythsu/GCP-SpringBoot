package com.example.SpringBoot.Error;

import com.example.SpringBoot.Services.Error.WebRequestException;
import com.example.SpringBoot.Services.Error.baseExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends baseExceptionHandler {

    @ExceptionHandler(WebRequestException.class)
    public WebRequestExceptionDto handleException(WebRequestException ex){
        return super.handleException(ex);
    }

}
