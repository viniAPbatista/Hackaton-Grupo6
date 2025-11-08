package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.StatusTask;
import com.hackaton.grupo6.enums.TaskPriority;
import com.hackaton.grupo6.model.UserComentary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponseDTO(

        UUID idTask,
        String name,
        String about,
        String idManager,
        String idEmployee,
        TaskPriority taskPriority,
        String teamId,
        StatusTask statusTask,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double estimatedTime,
        Double timeSpent,
        List<UserComentary>comentary
) {
}
