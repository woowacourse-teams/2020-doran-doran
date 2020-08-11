package com.grasshouse.dorandoran.common.exception;

public class NotValidDateException extends ExpectedException {

    public static final String NOT_VALID_DATE_AREA_MESSAGE = "올바른 날짜 구간을 입력해주세요.";
    public static final String NOT_VALID_DATE_FORMAT_MESSAGE = "올바른 날짜 형식을 입력해주세요.";

    public NotValidDateException(String message) {
        super(message);
    }
}
