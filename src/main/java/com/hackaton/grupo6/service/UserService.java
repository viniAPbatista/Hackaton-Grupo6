package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.*;
import com.hackaton.grupo6.enums.ActiveStatus;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // INJETADO

    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO userRequest) {

        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setEmail(userRequest.email());

        // Criptografa a senha antes de salvar
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));

        newUser.setRole(userRequest.role());
        newUser.setActiveStatus(ActiveStatus.ACTIVE);

        userRepository.save(newUser);

        return new RegisterUserResponseDTO(
                newUser.getName(),
                newUser.getEmail()
        );
    }

    public GetUserResponseDTO getUser(UUID id) {

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

        if (userRequest.name() != null && !userRequest.name().isBlank()) {
            user.setName(userRequest.name());
        }

        if (userRequest.email() != null && !userRequest.email().isBlank()) {
            user.setEmail(userRequest.email());
        }

        if (userRequest.role() != null) {
            user.setRole(userRequest.role());
        }

        if (userRequest.team() != null) {
            user.setTeam(userRequest.team());
        }

        // Corrigido: agora criptografa nova senha se enviada
        if (userRequest.password() != null && !userRequest.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.password()));
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

    private User findUserById(UUID id) {

        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    public void userLogin(LoginRequestDTO dto) {

        var usuario = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean senhaCorreta = passwordEncoder.matches(dto.password(), usuario.getPassword());

        if (!senhaCorreta) {
            throw new RuntimeException("Senha incorreta");
        }
    }
}
