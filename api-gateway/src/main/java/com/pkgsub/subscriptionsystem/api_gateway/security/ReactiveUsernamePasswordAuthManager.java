package com.pkgsub.subscriptionsystem.api_gateway.security;

import com.pkgsub.subscriptionsystem.common.exceptions.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReactiveUsernamePasswordAuthManager implements ReactiveAuthenticationManager {

    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String rawPwd = authentication.getCredentials().toString();

        return userDetailsService.findByUsername(username)
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")))
            .flatMap(userDetails -> {
                if (!passwordEncoder.matches(rawPwd, userDetails.getPassword())) {
                    return Mono.error(new BadCredentialsException("Invalid credentials"));
                }
                return Mono.just(new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                ));
            });
    }
}
