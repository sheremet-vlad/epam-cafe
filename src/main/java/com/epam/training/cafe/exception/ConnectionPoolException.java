package com.epam.training.cafe.exception;

public class ConnectionPoolException extends CafeAppException {

    private static final long serialVersionUID = -6164737102941474982L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
