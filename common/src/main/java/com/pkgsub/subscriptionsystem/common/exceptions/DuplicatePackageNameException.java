package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatePackageNameException extends BaseException{
    public DuplicatePackageNameException(HttpStatus status) {
        super(status);
    }

    public DuplicatePackageNameException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public DuplicatePackageNameException(Integer code, String message) {
        super(code, message);
    }

    public DuplicatePackageNameException(String message) {
        super(message);
    }

    public DuplicatePackageNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
