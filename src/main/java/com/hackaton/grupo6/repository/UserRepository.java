package com.hackaton.grupo6.repository;

import com.hackaton.grupo6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
