package com.luv2code.books.exception;

public class BookErrorResponse {

    private int statusCode;
    private String message;
    private long timeStamp;

    public BookErrorResponse(int statusCode, long timeStamp, String message) {
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
