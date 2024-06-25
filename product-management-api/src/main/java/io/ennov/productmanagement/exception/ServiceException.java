package io.ennov.productmanagement.exception;

/**
 * @author : t.randrianarisoa
 * @project : product-manager application
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }
}
