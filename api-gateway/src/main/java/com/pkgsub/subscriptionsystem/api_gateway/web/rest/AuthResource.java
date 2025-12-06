package com.pkgsub.subscriptionsystem.api_gateway.web.rest;

import com.pkgsub.subscriptionsystem.api_gateway.security.jwt.services.JwtService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwtToken = jwtService.generateTokenFromUsername(userDetails);
        return ResponseEntity.ok(ApiResponse.success(
                new LoginResponseDTO(userDetails.getUsername(), jwtToken, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
        ));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserDto>> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, null, userService.registerUser(request)));
    }
}