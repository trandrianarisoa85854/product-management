package io.ennov.productmanagement.service.user;

import io.ennov.productmanagement.model.domain.User;
import io.ennov.productmanagement.model.dto.AuthRequestDto;
import io.ennov.productmanagement.model.dto.RegisterDto;
import org.springframework.security.core.Authentication;

public interface UserService {
    User register(RegisterDto request);

    Authentication authenticate(AuthRequestDto request);
}
