package com.pkgsub.subscriptionsystem.api_gateway.security.service;

import com.pkgsub.subscriptionsystem.api_gateway.entity.User;
import com.pkgsub.subscriptionsystem.api_gateway.repository.UserRepository;
import com.pkgsub.subscriptionsystem.common.exceptions.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(HttpStatus.NOT_FOUND, "User with username [%s] is not found".formatted(username)));
        return new UserDetailsImpl(user);
    }
}