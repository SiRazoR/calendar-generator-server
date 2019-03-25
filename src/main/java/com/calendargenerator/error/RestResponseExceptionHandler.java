package com.calendargenerator.error;

import com.calendargenerator.exception.DataNotFoundException;
import com.calendargenerator.exception.GenericException;
import com.calendargenerator.model.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log;

    public RestResponseExceptionHandler() {
        super();
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class, DataNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        log.error("404 Status Code");
        ex.printStackTrace();
        ErrorMessage response = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND, "TODO"); //TODO documentation
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class, GenericException.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        log.error("500 Status Code");
        ex.printStackTrace();
        ErrorMessage response = new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "TODO"); //TODO documentation
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}