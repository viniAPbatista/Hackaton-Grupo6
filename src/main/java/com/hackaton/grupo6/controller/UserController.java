package com.hackaton.grupo6.controller;

import com.hackaton.grupo6.dto.*;
import com.hackaton.grupo6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public RegisterUserResponseDTO registerUser (@RequestBody RegisterUserRequestDTO userRequest) {
        return userService.registerUser(userRequest);
    }

    @GetMapping("/{id}")
    public GetUserResponseDTO getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public GetUserResponseDTO updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequestDTO updateRequest) {
        return userService.updateUser(id, updateRequest);
    }

    @PostMapping("/login")
    public void userLogin(@RequestBody LoginRequestDTO dto) {
        userService.userLogin(dto);
    }
}
