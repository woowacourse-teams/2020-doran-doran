package com.grasshouse.dorandoran.common;

import com.grasshouse.dorandoran.common.exception.ExpectedException;
import com.grasshouse.dorandoran.common.exception.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CommonAdvice.class);

    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "처리할 수 없는 요청입니다.";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(RuntimeException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(UNEXPECTED_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(ExpectedException.class)
    public ResponseEntity<ErrorResponse> handleExpectedException(ExpectedException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }
}
