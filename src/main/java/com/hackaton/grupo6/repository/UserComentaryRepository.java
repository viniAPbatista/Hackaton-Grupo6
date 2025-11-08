package com.hackaton.grupo6.repository;

import com.hackaton.grupo6.model.UserComentary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserComentaryRepository extends JpaRepository<UserComentary, UUID> {
}
