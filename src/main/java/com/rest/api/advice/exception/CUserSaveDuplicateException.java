package com.rest.api.advice.exception;

public class CUserSaveDuplicateException extends RuntimeException {

    public CUserSaveDuplicateException() {
        super();
    }

    public CUserSaveDuplicateException(String message) {
        super(message);
    }

    public CUserSaveDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
