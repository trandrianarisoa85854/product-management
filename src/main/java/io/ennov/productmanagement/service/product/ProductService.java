package io.ennov.productmanagement.service.product;

import io.ennov.productmanagement.model.domain.Product;
import io.ennov.productmanagement.model.dto.ProductDto;

import java.util.List;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
public interface ProductService {
    List<Product> retrieves();

    Product retrieve(Long productId);

    Product update(ProductDto productDto, Long id);

    Product create(ProductDto productDto);

    Boolean delete(Long productId);

}
