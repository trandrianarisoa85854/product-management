package io.ennov.productmanagement.repository;

import io.ennov.productmanagement.model.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT product FROM Product product JOIN FETCH product.user")
    List<Product> findProducts();

    @Modifying
    @Query("DELETE FROM Product WHERE id=:id")
    void deleteById(@Param("id") Long id);
}
