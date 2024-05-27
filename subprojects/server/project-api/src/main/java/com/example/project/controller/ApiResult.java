package com.example.project.controller;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PROTECTED)
public class ApiResult<T> {
    public static final String DEFAULT_SUCCESS_RESULT = "success";

    private final T result;

    private final ApiError error;

    private ApiResult(T result, ApiError error) {
        this.result = result;
        this.error = error;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    protected static class ApiError {
        private final String message;
    }

    public static ApiResult<String> success() {
        return success(DEFAULT_SUCCESS_RESULT);
    }

    public static <T> ApiResult<T> success(T result) {
        return new ApiResult<>(result, null);
    }


    public static <T> ApiResult<T> error(Throwable e) {
        return error(e.getMessage());
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(null, ApiError.of(message));
    }

    public String errorMessage() {
        return Objects.requireNonNullElse(error.getMessage(), "");
    }
}
