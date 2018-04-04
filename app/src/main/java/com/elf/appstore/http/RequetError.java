package com.elf.appstore.http;

/**
 * Created by antino on 18-3-26.
 */

public class RequetError extends java.lang.Exception {
    public static final int ERROR_OTHER = 0;
    public static final int ERROR_JSONPARSE = 1;
    public static final int ERROR_NO_NETWORK = 2;
    public static final int ERROR_TIMEOUT = 3;
    public static final int ERROR_NETWORK = 4;
    private int errorType = ERROR_OTHER;

    private String otherErrorValue;

    public RequetError() {
        super();
    }

    public RequetError(int errorType) {
        this.errorType = errorType;
    }

    public RequetError(String detailMessage) {
        super(detailMessage);
    }

    public RequetError(int errorType, String detailMessage) {
        super(detailMessage);
        this.errorType = errorType;
    }

    public RequetError(int errorType, String detailMessage, String otherError) {
        this(errorType, detailMessage);
        this.otherErrorValue = otherError;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getOtherErrorValue() {
        return otherErrorValue;
    }

    public void setOtherErrorValue(String otherErrorValue) {
        this.otherErrorValue = otherErrorValue;
    }
}
