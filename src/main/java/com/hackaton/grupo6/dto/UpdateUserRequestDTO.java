package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.UserEnum;
import com.hackaton.grupo6.model.Team;

import java.util.UUID;

public record UpdateUserRequestDTO(String name,
                                   String email,
                                   UserEnum role,
                                   Team team,
                                   String password) {
}
