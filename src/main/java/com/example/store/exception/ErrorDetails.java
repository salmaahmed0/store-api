package com.example.store.exception;

import lombok.Data;

@Data
public class ErrorDetails {
    private int status;
    private String message;
    private long timeStamp;
}
