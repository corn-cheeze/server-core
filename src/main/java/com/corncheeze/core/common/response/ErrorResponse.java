package com.corncheeze.core.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse<T> {

    private final String message;

    private final T data;

    @Builder
    public ErrorResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
