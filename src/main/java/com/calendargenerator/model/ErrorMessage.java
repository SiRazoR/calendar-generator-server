package com.calendargenerator.model;

import org.springframework.http.HttpStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ErrorMessage {
    @Id
    private UUID id;
    private String errorCodePhrase;
    private int errorCodeValue;
    private String errorMessage;

    public ErrorMessage() {
        this.id = UUID.randomUUID();
    }

    public ErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this();
        this.errorMessage = errorMessage;
        this.errorCodePhrase = httpStatus.getReasonPhrase();
        this.errorCodeValue = httpStatus.value();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCodePhrase() {
        return errorCodePhrase;
    }

    public void setErrorCodePhrase(String errorCodePhrase) {
        this.errorCodePhrase = errorCodePhrase;
    }

    public int getErrorCodeValue() {
        return errorCodeValue;
    }

    public void setErrorCodeValue(int errorCodeValue) {
        this.errorCodeValue = errorCodeValue;
    }
}
