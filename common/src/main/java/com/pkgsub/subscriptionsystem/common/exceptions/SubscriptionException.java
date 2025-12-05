package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class SubscriptionException extends BaseException {
    public SubscriptionException(HttpStatus status) {
        super(status);
    }

    public SubscriptionException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public SubscriptionException(Integer code, String message) {
        super(code, message);
    }

    public SubscriptionException(String message) {
        super(message);
    }

    public SubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
