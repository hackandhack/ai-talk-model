package com.aitalk;

/**
 * Custom exception for Grok API related errors.
 */
public class GrokApiException extends Exception {

    public GrokApiException(String message) {
        super(message);
    }

    public GrokApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
