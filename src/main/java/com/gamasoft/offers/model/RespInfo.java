package com.gamasoft.offers.model;

public class RespInfo {
    // Value object to represent information on http commands (POST, PUT, DELETE)
    public final String message;
    public final int httpStatus;
    public final String resouceUri;

    public RespInfo(String message, int httpStatus, String resouceUri) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.resouceUri = resouceUri;
    }
}
