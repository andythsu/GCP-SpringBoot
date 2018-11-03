package com.example.SpringBoot.Error;

//import org.github.andythsu.GCP.Services.Error.WebRequestException;
//import org.github.andythsu.GCP.Services.Error.baseExceptionHandler;
import Services.Error.WebRequestException;
import Services.Error.baseExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends baseExceptionHandler {

    @ExceptionHandler(WebRequestException.class)
    public ResponseEntity<?> handleException(WebRequestException ex){
        return super.handleException(ex);
    }

}
