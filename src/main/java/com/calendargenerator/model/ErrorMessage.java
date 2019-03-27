package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ErrorMessage {
    @Id
    private UUID id = UUID.randomUUID();
    private String errorCodePhrase;
    private int errorCodeValue;
    private String errorMessage;

    public ErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCodePhrase = httpStatus.getReasonPhrase();
        this.errorCodeValue = httpStatus.value();
    }

}
