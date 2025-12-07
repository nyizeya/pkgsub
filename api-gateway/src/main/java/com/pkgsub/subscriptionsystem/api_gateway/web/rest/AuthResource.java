package com.pkgsub.subscriptionsystem.api_gateway.web.rest;

import com.pkgsub.subscriptionsystem.api_gateway.security.jwt.services.JwtService;
import com.pkgsub.subscriptionsystem.api_gateway.security.service.UserDetailsImpl;
import com.pkgsub.subscriptionsystem.api_gateway.service.UserService;
import com.pkgsub.subscriptionsystem.common.dto.request.LoginRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.SignUpRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.ApiResponse;
import com.pkgsub.subscriptionsystem.common.dto.response.LoginResponseDTO;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import com.pkgsub.subscriptionsystem.common.exceptions.BadCredentialsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    private final JwtService jwtService;
    private final UserService userService;
    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    @PostMapping("/sign-in")
    public Mono<ResponseEntity<ApiResponse<LoginResponseDTO>>> login(@Valid @RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        return reactiveAuthenticationManager.authenticate(authenticationToken)
                .flatMap(authentication -> {
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    String jwtToken = jwtService.generateTokenFromUsername(userDetails);

                    Set<String> roles = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet());

                    LoginResponseDTO responseDTO = new LoginResponseDTO(
                            userDetails.getUser().getId(),
                            userDetails.getUser().getEmail(),
                            userDetails.getUsername(),
                            jwtToken,
                            roles,
                            userDetails.getUser().getBalance()
                    );

                    return Mono.just(ResponseEntity.ok(ApiResponse.success(responseDTO)));
                })
                .onErrorResume(AuthenticationException.class, e -> Mono.error(new BadCredentialsException("Bad credentials")));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserDto>> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, null, userService.registerUser(request)));
    }
}