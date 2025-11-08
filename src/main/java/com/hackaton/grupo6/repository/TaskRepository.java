package com.hackaton.grupo6.repository;

import com.hackaton.grupo6.dto.TaskResponseDTO;
import com.hackaton.grupo6.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task,UUID> {

    List<Task> findByIdEmployee_IdUser(UUID idEmployee);
    List<Task> findByIdManager_IdUser(UUID idManager);
    List<Task> findByTeam_Id(UUID id);

}
