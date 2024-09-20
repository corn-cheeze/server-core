package com.corncheeze.core.common.exception.handler;

import com.corncheeze.core.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    private void logging(Exception e) {
        log.error("", e);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse<String> exceptionHandler(IllegalArgumentException e) {
        logging(e);

        return ErrorResponse.<String>builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(Exception.class)
    public ErrorResponse<String> exceptionHandler(Exception e) {
        logging(e);

        return ErrorResponse.<String>builder()
                .message("Please contact a administrator")
                .build();
    }
}
