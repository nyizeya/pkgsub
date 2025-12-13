package com.pkgsub.subscriptionsystem.api_gateway.security.service;

import com.pkgsub.subscriptionsystem.api_gateway.repository.UserRepository;
import com.pkgsub.subscriptionsystem.common.exceptions.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findByUsername(username))
                .flatMap(optionalUser -> optionalUser
                        .map(user -> Mono.just(new UserDetailsImpl(optionalUser.get())))
                        .orElseGet(() -> Mono.error(new UsernameNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "User with username [%s] is not found".formatted(username))))
                );
    }
}