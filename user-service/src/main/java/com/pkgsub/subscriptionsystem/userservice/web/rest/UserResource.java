package com.pkgsub.subscriptionsystem.userservice.web.rest;

import java.util.List;

import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserCreateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.ApiResponse;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import com.pkgsub.subscriptionsystem.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserCreateRequest dto) {
        UserDto createdUser = userService.createUser(dto);
        return new ResponseEntity<>(ApiResponse.of(HttpStatus.CREATED, null, createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable String id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserUpdateDto dto) {
//        UserDto updatedUser = userService.updateUser(id, dto);
//        return ResponseEntity.ok(updatedUser);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/debit/fund")
    public ResponseEntity<Void> debitFunds(@Valid @RequestBody UserBalanceDebitRequest request) {
        userService.debit(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/credit/fund")
    public ResponseEntity<Void> creditFunds(@Valid @RequestBody UserBalanceCreditRequest request) {
        userService.credit(request);
        return ResponseEntity.noContent().build();
    }
}