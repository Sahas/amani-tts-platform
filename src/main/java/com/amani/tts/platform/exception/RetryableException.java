package com.amani.tts.platform.exception;

public class RetryableException extends RuntimeException {
  
    public RetryableException(Throwable cause) {
        super(cause);
    }

    public RetryableException(String message) {
        super(message);
    }
}
