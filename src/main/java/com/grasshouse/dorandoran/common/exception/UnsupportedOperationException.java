package com.grasshouse.dorandoran.common.exception;

public class UnsupportedOperationException extends ExpectedException {

    private static final String MESSAGE = "구현되지 않은 기능입니다.";

    public UnsupportedOperationException() {
        super(MESSAGE);
    }
}
