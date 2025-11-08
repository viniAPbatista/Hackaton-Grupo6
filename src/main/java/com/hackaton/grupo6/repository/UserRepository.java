package com.hackaton.grupo6.repository;

import com.hackaton.grupo6.dto.RegisterUserResponseDTO;
import com.hackaton.grupo6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<RegisterUserResponseDTO> findByTeam_Id(UUID idTeam);

    Optional<User> findByEmail(String email);

}
