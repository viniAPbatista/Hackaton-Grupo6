package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.StatusTask;

import java.util.UUID;

public record ChangeStatusTaskRequestDTO(
        UUID id, StatusTask status
) {
}

