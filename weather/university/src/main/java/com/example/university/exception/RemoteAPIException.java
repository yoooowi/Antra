package com.example.university.exception;

public class RemoteAPIException extends RuntimeException{
    private String url;

    public RemoteAPIException(String url, String message) {
        super(message);
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
