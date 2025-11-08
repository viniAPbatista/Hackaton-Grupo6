package com.hackaton.grupo6.repository;

import com.hackaton.grupo6.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
