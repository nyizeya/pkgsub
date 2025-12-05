package com.pkgsub.subscriptionsystem.userservice.service;

import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserCreateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateRequest userCreateRequest);

    UserDto getUserById(String id);

    UserDto getUserByUsername(String username);

    List<UserDto> getAllUsers();

    // UserDto updateUser(String id, UserUpdateDto dto);

    void deleteUser(String id);

    void debit(UserBalanceDebitRequest userBalanceDebitRequest);

    void credit(UserBalanceCreditRequest userBalanceCreditRequest);
}