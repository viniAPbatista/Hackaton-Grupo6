package com.hackaton.grupo6.dto;

import java.util.UUID;

public record UserGetTaskRequest(UUID idTask, UUID idUser) {
}
