package io.ennov.productmanagement.exception;

/**
 * @author : t.randrianarisoa
 * @project : product-manager application
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}