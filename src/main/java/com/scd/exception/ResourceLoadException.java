package com.scd.exception;

/**
 * @author James
 */
public class ResourceLoadException extends RuntimeException {
    public ResourceLoadException(String message) {
        super(message);
    }

    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
