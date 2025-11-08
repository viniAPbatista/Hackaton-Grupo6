package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.ActiveStatus;

public record SetFeriasResponseDTO(String nome, String email, ActiveStatus activeStatus) {
}
