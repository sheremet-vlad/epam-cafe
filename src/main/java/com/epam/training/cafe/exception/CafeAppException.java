package com.epam.training.cafe.exception;

public class CafeAppException extends Exception {

    public CafeAppException() {
        super();
    }

    public CafeAppException(String message) {
        super(message);
    }

    public CafeAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
