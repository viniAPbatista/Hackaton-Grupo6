package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.User;

import java.util.List;
import java.util.UUID;

public record TeamResponseDTO(UUID team,
                              String name,
                              List<User> user,
                              List<Task> tasks){

}
