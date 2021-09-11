package ru.javaops.topjava.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class VoteDeadlineException extends AppException {
    public VoteDeadlineException(String msg) {
        super(HttpStatus.METHOD_NOT_ALLOWED, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}
