package io.ennov.productmanagement.service.product;

import io.ennov.productmanagement.exception.ProductNotFoundException;
import io.ennov.productmanagement.exception.ServiceException;
import io.ennov.productmanagement.model.domain.Product;
import io.ennov.productmanagement.model.dto.ProductDto;
import io.ennov.productmanagement.repository.ProductRepository;
import io.ennov.productmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository    userRepository;

    /**
     * Get all product
     *
     * @return
     */
    @Override
    public List<Product> retrieves() {
        try {
            return productRepository.findAll();
        } catch (Exception ex) {
            throw new ServiceException("retrieve all product failed");
        }

    }

    /**
     * Get product for id
     *
     * @param productId
     * @return
     */
    @Override
    public Product retrieve(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    /**
     * Update product
     *
     * @param productDto
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Product update(ProductDto productDto, Long id) {
        try {
            Product product = this.retrieve(id);
            product.setTitle(productDto.title());
            product.setPrice(productDto.price());
            product.setDescription(productDto.description());
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new ServiceException("Update product failed");
        }
    }

    /**
     * Persist product
     *
     * @param productDto
     * @return
     */
    @Transactional
    @Override
    public Product create(ProductDto productDto) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Product product = Product.builder()
                    .title(productDto.title())
                    .price(productDto.price())
                    .description(productDto.description())
                    .user(userRepository.findByEmail(user.getUsername()).get())
                    .build();
            return productRepository.save(product);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ServiceException("Persist product failed");
        }
    }


    /**
     * Delete product
     *
     * @param productId
     */
    @Transactional
    @Override
    public Boolean delete(Long productId) {
        Product product = this.retrieve(productId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!product.getUser().equals(userRepository.findByEmail(user.getUsername()).get())) {
            throw new ServiceException("Delete product failed, user not authorized");
        }

        try {
            productRepository.deleteById(productId);
            return Boolean.TRUE;
        } catch (Exception ex) {
            throw new ServiceException("Delete product failed");
        }
    }
}
