package ru.javaops.topjava.error;

public class VoteDeadlineException extends RuntimeException {
    public VoteDeadlineException(String message) {
        super(message);
    }
}
