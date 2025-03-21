package com.credable.lms.exception;

public class MaxRetriesExceededException extends IntegrationException {
    public MaxRetriesExceededException(String message) {
        super(message);
    }
}
