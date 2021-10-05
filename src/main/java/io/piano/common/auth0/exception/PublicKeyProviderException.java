package io.piano.common.auth0.exception;


import io.piano.common.auth0.utils.tokens.PublicKeyProvider;

/**
 * Represents an error when attempting to retrieve a public key.
 * @see PublicKeyProvider
 */
@SuppressWarnings("unused")
public class PublicKeyProviderException extends Exception {

    /**
     * Creates a new {@code PublicKeyProviderException}
     *
     * @param message the exception message
     */
    public PublicKeyProviderException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code PublicKeyProviderException}
     *
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public PublicKeyProviderException(String message, Throwable cause) {
        super(message, cause);
    }

}
