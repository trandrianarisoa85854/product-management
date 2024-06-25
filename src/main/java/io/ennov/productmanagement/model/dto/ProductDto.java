package io.ennov.productmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
public record ProductDto(Long id,
                         @NotBlank(message = "The product's title is required.")
                         String title,
                         @NotNull(message = "The product's price is required.")
                         Double price,
                         @NotBlank(message = "The product's description is required.")
                         String description) {
}
