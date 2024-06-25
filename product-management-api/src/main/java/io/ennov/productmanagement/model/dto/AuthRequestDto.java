package io.ennov.productmanagement.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
public record AuthRequestDto(
        @NotBlank(message = "The username is required.")
        @Email(message = "The email address is invalid.", flags = {Pattern.Flag.CASE_INSENSITIVE})
        String username,
        @NotNull(message = "The password is required.")
        String password) {
}
