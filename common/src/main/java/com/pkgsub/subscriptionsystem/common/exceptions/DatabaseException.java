package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class DatabaseException extends BaseException {
    public DatabaseException(HttpStatus status) {
        super(status);
    }

    public DatabaseException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public DatabaseException(Integer code, String message) {
        super(code, message);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}