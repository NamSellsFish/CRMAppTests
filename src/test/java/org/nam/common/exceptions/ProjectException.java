package org.nam.common.exceptions;

/**
 * FrameworkException extends RuntimeException - because I want to terminate the program when the Exception
 * comes
 */
@SuppressWarnings("serial")
public class ProjectException extends RuntimeException {

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
