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
    public WebRequestExceptionDto handleException(WebRequestException ex) {
        return new WebRequestExceptionDto(ex);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity handleGeneralException(Throwable t) {
        return new ResponseEntity(new ThrowableDto(t), getStatus(BAD_STATUS));
    }

    private HttpStatus getStatus(int status) {
        return HttpStatus.resolve(status);
    }

    // dto class for general throable exceptions
    public static class ThrowableDto extends MessageKey {

        public ThrowableDto(Throwable t) {
            this(t.getMessage());
        }

        protected ThrowableDto(String message) {
            super.tag(MessageKeyTags.RUN_TIME_ERROR).message(message);
        }

    }

    //dto class for customizing WebRequestException fields
    public static class WebRequestExceptionDto {
        private MessageKey messageKey;
        private String[] errorProperties;

        public WebRequestExceptionDto(WebRequestException ex) {
            this.messageKey = ex.getMessageKey();
            this.errorProperties = ex.getErrorProperties();
        }

        public MessageKey getMessageKey() {
            return messageKey;
        }

        public String[] getErrorProperties() {
            return errorProperties;
        }
    }
}
