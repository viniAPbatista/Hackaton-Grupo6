package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.StatusTask;
import com.hackaton.grupo6.enums.TaskPriority;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequestDTO(
        String name,
        String about,
        UUID idManager,
        UUID idEmployee,
        TaskPriority taskPriority,
        UUID teamId,
        StatusTask statusTask,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double estimatedTime,
        Double timeSpent
) {}