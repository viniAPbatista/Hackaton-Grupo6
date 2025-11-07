package com.hackaton.grupo6.repository;

import com.hackaton.grupo6.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task,UUID> {
}
