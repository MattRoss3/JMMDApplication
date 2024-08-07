package com.example.jmmdapplication.util;

/**
 * Custom exception class to indicate that a user was not found.
 * <p>
 * This exception should be thrown when a user lookup fails or when a user cannot be found in the system.
 * </p>
 */

public class UserNotFoundException extends Exception{

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail message.
     *
     * @param message The detail message, which provides more information about the cause of the exception.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
