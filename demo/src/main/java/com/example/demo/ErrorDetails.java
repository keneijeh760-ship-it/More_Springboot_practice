package com.example.demo;

import java.time.LocalDateTime;

public class ErrorDetails {
    private String message;
    private String Details;
    private LocalDateTime timestamp;


    public ErrorDetails(String message, String Details, LocalDateTime timestamp){
        this.message = message;
        this.Details = Details;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return Details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
