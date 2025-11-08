package com.hackaton.grupo6.dto;

import java.util.UUID;

public record ComentaryRequestDTO(String comentary,
                                  UUID task,
                                  UUID user) {
}
