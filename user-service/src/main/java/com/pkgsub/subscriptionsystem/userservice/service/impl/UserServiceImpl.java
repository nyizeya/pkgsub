package com.pkgsub.subscriptionsystem.userservice.service.impl;

import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserCreateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import com.pkgsub.subscriptionsystem.common.exceptions.EntityNotFoundException;
import com.pkgsub.subscriptionsystem.common.exceptions.TransactionValidationException;
import com.pkgsub.subscriptionsystem.userservice.entity.AppUserEntity;
import com.pkgsub.subscriptionsystem.userservice.repository.UserRepository;
import com.pkgsub.subscriptionsystem.userservice.service.UserService;
import com.pkgsub.subscriptionsystem.userservice.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder; // Must be configured as a bean

    @Override
    @Transactional
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.findByUsername(userCreateRequest.getUsername()).isPresent()) {
             throw new IllegalStateException("Username [%s] is already taken.".formatted(userCreateRequest.getUsername()));
        }

        if (userRepository.findByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email [%s] is already taken.".formatted(userCreateRequest.getEmail()));
        }

        AppUserEntity user = AppUserEntity.builder()
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .encodedPassword(userCreateRequest.getPassword())
                .role(AppUserRole.USER)
                .createdAt(LocalDate.now())
                .active(true)
                .balance(new BigDecimal(10000))
                .build();

        AppUserEntity savedUser = userRepository.save(user);
        log.info("User created: {}", savedUser.getUsername());
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDto getUserById(String id) {
        return userMapper.toDTO(findById(id));
    }

    @Override
    public UserDto getUserByUsername(String username) {
         return userRepository.findByUsername(username)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "User not found with username: [%s]".formatted(username)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        AppUserEntity entity = findById(id);
        userRepository.delete(entity);
        log.warn("User deleted: {}", entity.getUsername());
    }

    @Transactional
    @Override
    public void debit(UserBalanceDebitRequest userBalanceDebitRequest) {
        var entity = findById(userBalanceDebitRequest.getUserId());

        if (entity.getBalance().compareTo(userBalanceDebitRequest.getAmount()) < 0) {
            throw new TransactionValidationException(HttpStatus.BAD_REQUEST, "User does not have enough balance.");
        }

        entity.setBalance(entity.getBalance().subtract(userBalanceDebitRequest.getAmount()));
        userRepository.save(entity);
    }

    @Transactional
    @Override
    public void credit(UserBalanceCreditRequest userBalanceCreditRequest) {
        var entity = findById(userBalanceCreditRequest.getUserId());
        entity.setBalance(entity.getBalance().add(userBalanceCreditRequest.getAmount()));
        userRepository.save(entity);
    }

    private AppUserEntity findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "User with ID [%s] is not found".formatted(id)));
    }
}