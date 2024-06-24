package com.example.university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UniversityExceptionHandler {

    @ExceptionHandler(RemoteAPIException.class)
    public ResponseEntity<String> remoteAPIException(RemoteAPIException e) {
        return new ResponseEntity<>("An error occurred when accessing " + e.getUrl(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> invalidMethod(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultHandler(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("An error occurred at the server side", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
