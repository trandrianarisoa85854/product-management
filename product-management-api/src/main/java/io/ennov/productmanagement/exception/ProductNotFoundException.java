package io.ennov.productmanagement.exception;

/**
 * @author : t.randrianarisoa
 * @project : product-manager application
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Could not find product with id : " + id);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}