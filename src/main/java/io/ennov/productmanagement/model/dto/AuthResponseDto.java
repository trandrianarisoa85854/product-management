package io.ennov.productmanagement.model.dto;

import java.util.List;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
public record AuthResponseDto(String accessToken, List<String> roles, String userConnected) {
}
