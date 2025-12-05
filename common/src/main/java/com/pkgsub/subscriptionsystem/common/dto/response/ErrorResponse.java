package com.pkgsub.subscriptionsystem.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pkgsub.subscriptionsystem.common.utils.ExceptionUtils;
import com.pkgsub.subscriptionsystem.common.vo.ValidationError;
import jakarta.validation.ConstraintViolation;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private int status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String stackTrace;

    private List<ValidationError> errors;

    public static ErrorResponse of(HttpStatus status, Throwable ex) {
        return ErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .message(Optional.of(ex.getMessage()).orElse(status.getReasonPhrase()))
                .stackTrace(ExceptionUtils.getStackTrace(ex.getCause()))
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse ofSuccess() {
        return ErrorResponse.builder()
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse ofStatus(HttpStatus status) {
        return ErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .errors(new ArrayList<>())
                .build();
    }

    public static <T extends Throwable> ErrorResponse ofException(HttpStatus status, T t) {
        return of(status, t);
    }

    public static <T extends Throwable> ErrorResponse ofException(T t) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(t.getMessage())
                .stackTrace(ExceptionUtils.getStackTrace(t.getCause()))
                .errors(new ArrayList<>())
                .build();
    }

    public void addFieldValidationErrors(List<FieldError> fieldErrors) {
        Optional.ofNullable(fieldErrors)
                .ifPresent(errors -> errors.forEach(this::addValidationError));
    }

    public void addGlobalValidationErrors(List<ObjectError> globalErrors) {
        Optional.ofNullable(globalErrors)
                .ifPresent(errors -> errors.forEach(this::addValidationError));
    }

    public void addConstraintValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        Optional.ofNullable(constraintViolations)
                .ifPresent(violations -> violations.forEach(this::addValidationError));
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(ValidationError.builder()
                .object(object)
                .field(field)
                .rejectedValue(rejectedValue)
                .message(message)
                .build());
    }

    private void addValidationError(String object, String message) {
        addSubError(ValidationError.builder()
                .object(object)
                .message(message)
                .build());
    }

    private void addValidationError(FieldError fieldError) {
        addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
        );
    }

    private void addValidationError(ObjectError objectError) {
        addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    private void addValidationError(ConstraintViolation<?> cv) {
        addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                cv.getPropertyPath().toString(),
                cv.getInvalidValue(),
                cv.getMessage()
        );
    }

    private void addSubError(ValidationError subError) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(subError);
    }
}