package com.pkgsub.subscriptionsystem.api_gateway.service.impl;

import com.pkgsub.subscriptionsystem.api_gateway.entity.User;
import com.pkgsub.subscriptionsystem.api_gateway.repository.UserRepository;
import com.pkgsub.subscriptionsystem.api_gateway.service.UserService;
import com.pkgsub.subscriptionsystem.api_gateway.web.mapper.UserMapper;
import com.pkgsub.subscriptionsystem.common.dto.request.SignUpRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import com.pkgsub.subscriptionsystem.common.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto registerUser(SignUpRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new IllegalStateException("Username [%s] is already taken.".formatted(signUpRequest.getUsername()));
        }

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email [%s] is already taken.".formatted(signUpRequest.getEmail()));
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setBalance(new BigDecimal(10000));

        return userMapper.toDTO(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        User entity = findById(id);
        userRepository.delete(entity);
        log.warn("User deleted: {}", entity.getUsername());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(String id) {
        return userMapper.toDTO(findById(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        var entity = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username [%s] is not found".formatted(username)));
        return userMapper.toDTO(entity);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    @Override
    public UserDto getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return getUserByUsername(userDetails.getUsername());
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id [%s] is not found".formatted(id)));
    }
}
