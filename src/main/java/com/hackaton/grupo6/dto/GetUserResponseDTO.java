package com.hackaton.grupo6.dto;

import com.hackaton.grupo6.enums.UserEnum;
import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.Team;

import java.util.List;
import java.util.UUID;

public record GetUserResponseDTO (UUID id,
                                  String name,
                                  String email,
                                  UserEnum role,
                                  Team team,
                                  List<Task> tasks,
                                  List<Task> taskCreated){
}
