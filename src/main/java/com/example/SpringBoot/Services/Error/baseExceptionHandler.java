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

    int BAD_STATUS = 500;

    @ExceptionHandler(WebRequestException.class)
    public ResponseEntity<?> handleException(WebRequestException ex) {
        MessageKey msg = ex.getMessageKey();
        return new ResponseEntity(msg, getStatus(msg.getStatus()));
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<?> handleGeneralException(Throwable t){
        if (t instanceof WebRequestException){
            return this.handleException((WebRequestException) t);
        }else{
            return new ResponseEntity(new ThrowableDto(t), getStatus(BAD_STATUS));
        }
    }

    public HttpStatus getStatus(int status){
        return HttpStatus.resolve(status);
    }

    // dto class for general throable exceptions
    public static class ThrowableDto extends MessageKey{
        private Throwable cause;
        public ThrowableDto(Throwable t){
            this(t.getMessage());
            this.cause = t.getCause();
        }
        protected ThrowableDto(String message) {
            super.tag(MessageKeyTags.RUN_TIME_ERROR).message(message);
        }
    }
}
