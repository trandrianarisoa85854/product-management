package io.ennov.productmanagement.exception;

/**
 * @author : t.randrianarisoa
 * @project : product-manager application
 */
public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
