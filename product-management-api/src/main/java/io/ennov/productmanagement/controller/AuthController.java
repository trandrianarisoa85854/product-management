package io.ennov.productmanagement.controller;

import io.ennov.productmanagement.exception.BadCredentialsException;
import io.ennov.productmanagement.model.dto.ApiErrorResponse;
import io.ennov.productmanagement.model.dto.AuthRequestDto;
import io.ennov.productmanagement.model.dto.AuthResponseDto;
import io.ennov.productmanagement.model.dto.RegisterDto;
import io.ennov.productmanagement.service.user.UserService;
import io.ennov.productmanagement.service.utils.JwtGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AuthController", description = "Api Authentication /api/v1/auth")
public class AuthController {
    private final UserService  userService;
    private final JwtGenerator jwtGenerator;

    @Operation(summary = "Authenticate user and return token")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponseDto.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        try {
            Authentication authentication = userService.authenticate(authRequestDto);
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            String token = jwtGenerator.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponseDto(token, roles, authRequestDto.username()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("username/password not correct");
        }
    }


    @Operation(summary = "Register user")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
