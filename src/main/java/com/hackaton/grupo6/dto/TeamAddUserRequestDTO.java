package com.hackaton.grupo6.dto;

import java.util.UUID;

public record TeamAddUserRequestDTO(UUID idTeam,
                                    UUID idUser) {
}
