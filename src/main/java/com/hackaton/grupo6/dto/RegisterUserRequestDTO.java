package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.UserEnum;

import java.util.UUID;

public record RegisterUserRequestDTO(String name,
                                     String email,
                                     String password,
                                     UserEnum role) {
}
