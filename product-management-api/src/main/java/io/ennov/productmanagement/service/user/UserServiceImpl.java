package io.ennov.productmanagement.service.user;

import io.ennov.productmanagement.model.domain.Role;
import io.ennov.productmanagement.model.domain.User;
import io.ennov.productmanagement.model.dto.AuthRequestDto;
import io.ennov.productmanagement.model.dto.RegisterDto;
import io.ennov.productmanagement.repository.RoleRepository;
import io.ennov.productmanagement.repository.UserRepository;
import io.ennov.productmanagement.service.utils.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository        userRepository;
    private final RoleRepository        roleRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        var user = User.builder()
                .firstname(registerDto.getFirstname())
                .lastname(registerDto.getLastname())
                .email(registerDto.getEmail())
                .password(encodedPassword)
                .build();

        Role roles = roleRepository.findByName(registerDto.getRole()).get();
        user.setRoles(Collections.singletonList(roles));

        return userRepository.save(user);
    }

    public Authentication authenticate(AuthRequestDto authRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.username(), authRequestDto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Authentication failed " + ex.getMessage());
        }
    }
}
