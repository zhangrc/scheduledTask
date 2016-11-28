package com.yinhai.sheduledTask.frame.exception;

/**
 * Created by zrc on 2016/11/28.
 */
public class AppException extends RuntimeException {
    private String errorMessage;
    private String fieldName;

    AppException() {
    }

    public AppException(String var1) {
        super(var1);
        this.errorMessage = var1;
    }

    public AppException(String var1, String var2) {
        super(var1);
        this.errorMessage = var1;
        this.fieldName = var2;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getMessage() {
        return this.errorMessage;
    }
}
