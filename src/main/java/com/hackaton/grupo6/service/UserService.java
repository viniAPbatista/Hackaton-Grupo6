package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.GetUserResponseDTO;
import com.hackaton.grupo6.dto.RegisterUserRequestDTO;
import com.hackaton.grupo6.dto.RegisterUserResponseDTO;
import com.hackaton.grupo6.dto.UpdateUserRequestDTO;
import com.hackaton.grupo6.enums.ActiveStatus;
import com.hackaton.grupo6.model.Team;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public RegisterUserResponseDTO registerUser (RegisterUserRequestDTO userRequest) {

        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setEmail(userRequest.email());
        newUser.setPassword(userRequest.password());
        newUser.setRole(userRequest.role());
        newUser.setActiveStatus(ActiveStatus.ACTIVE);

        userRepository.save(newUser);

        return new RegisterUserResponseDTO(
                newUser.getName(),
                newUser.getEmail()
        );
    }

    public GetUserResponseDTO getUser (UUID id) {

        User user = findUserById(id);

        return new GetUserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getTeam(),
                user.getTask(),
                user.getTaskCreated()
        );
    }

    public GetUserResponseDTO updateUser(UUID id, UpdateUserRequestDTO userRequest) {

        User user = findUserById(id);

        if(userRequest.name() != null && !userRequest.name().isBlank()) {
            user.setName(userRequest.name());
        }

        if(userRequest.email() != null && !userRequest.email().isBlank()) {
            user.setEmail(userRequest.email());
        }

        if(userRequest.role() != null) {
            user.setRole(userRequest.role());
        }

        if(userRequest.team() != null) {
            user.setTeam(userRequest.team());
        }

        if(userRequest.password() != null && userRequest.password().isBlank()) {
            user.setPassword(userRequest.password());
        }

        userRepository.save(user);

        return new GetUserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getTeam(),
                user.getTask(),
                user.getTaskCreated()
        );
    }

    public void deleteUser(UUID id) {

        User user = findUserById(id);

        user.setActiveStatus(ActiveStatus.INACTIVE);

        userRepository.save(user);
    }

    private User findUserById (UUID id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        return user;
    }
}
