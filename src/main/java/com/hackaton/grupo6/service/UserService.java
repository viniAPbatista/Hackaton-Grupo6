package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.*;
import com.hackaton.grupo6.enums.ActiveStatus;
import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.Team;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public SetFeriasResponseDTO setFerias(UUID id) {

        User user = findUserById(id);

        //coloca as ferias no usuario
        user.setActiveStatus(ActiveStatus.FERIAS);

        userRepository.save(user);

        //pega todos os usuarios
        List<User> users = userRepository.findAll();

        //pega os usuarios ativos
        List<User> activeUsers = users.stream()
                .filter(activeU -> activeU.getActiveStatus() == ActiveStatus.ACTIVE)
                .collect(Collectors.toList());

        //pega o usuario com menos tarefas
        Optional<User> userWithLeastTasks = activeUsers.stream()
                .min(Comparator.comparingInt(lessTask -> lessTask.getTask().size()));

        //pega a lista de tarefas do usuario inicial
        List<Task> tarefasUserInicial = user.getTask();

        //atribui todas as tarefas dele ao usuario com menos tarefas
        tarefasUserInicial.forEach(task -> task.setIdEmployee(userWithLeastTasks.get()));

        userRepository.save(userWithLeastTasks.get());

        return new SetFeriasResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getActiveStatus()
        );
    }

    private User findUserById (UUID id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        return user;
    }
}
