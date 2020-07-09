package com.grasshouse.dorandoran.common;

import com.grasshouse.dorandoran.common.exception.DoranDoranException;
import com.grasshouse.dorandoran.common.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonAdvice {

    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "처리할 수 없는 요청입니다.";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(UNEXPECTED_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(DoranDoranException.class)
    public ResponseEntity<ErrorResponse> handleExpectedException(DoranDoranException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(e.getMessage()));
    }
}
