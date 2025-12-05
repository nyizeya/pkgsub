package com.pkgsub.subscriptionsystem.common.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;

    private LocalDateTime timestamp;

    private String message;

    private T data;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .message(message != null ? message : status.getReasonPhrase())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(message)
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .message(message != null ? message : status.getReasonPhrase())
                .build();
    }

    public ApiResponse<T> withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        return this;
    }
}