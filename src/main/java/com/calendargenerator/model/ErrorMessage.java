package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ErrorMessage {
    private String errorCodePhrase;
    private int errorCodeValue;
    private String errorMessage;

    public ErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCodePhrase = httpStatus.getReasonPhrase();
        this.errorCodeValue = httpStatus.value();
    }
}
