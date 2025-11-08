package com.hackaton.grupo6.dto;


import com.hackaton.grupo6.model.User;

import java.util.List;

public record TeamRequestDTO(List<User> users, String name) {

}
