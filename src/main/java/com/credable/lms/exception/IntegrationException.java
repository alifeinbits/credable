package com.credable.lms.exception;

public class IntegrationException extends LmsException {
    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
