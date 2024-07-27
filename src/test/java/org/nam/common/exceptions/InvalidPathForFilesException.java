package org.nam.common.exceptions;

@SuppressWarnings("serial")
public class InvalidPathForFilesException extends RuntimeException {

    public InvalidPathForFilesException(String message) {
        super(message);
    }

    public InvalidPathForFilesException(String message, Throwable cause) {
        super(message, cause);
    }

}
