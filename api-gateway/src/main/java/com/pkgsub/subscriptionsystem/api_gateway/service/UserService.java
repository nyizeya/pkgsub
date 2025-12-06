package com.pkgsub.subscriptionsystem.api_gateway.service;

import com.pkgsub.subscriptionsystem.api_gateway.entity.User;
import com.pkgsub.subscriptionsystem.common.dto.request.SignUpRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto registerUser(SignUpRequest request);

    List<UserDto> getAllUsers();

    UserDto getUserById(String id);

    UserDto getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    List<UserDto> findAllUsers();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteUser(String id);

    UserDto getUserFromAuthentication();
}