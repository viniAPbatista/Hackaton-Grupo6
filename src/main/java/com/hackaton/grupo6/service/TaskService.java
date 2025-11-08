package com.hackaton.grupo6.service;

import com.hackaton.grupo6.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.TaskRepository;
import com.hackaton.grupo6.repository.UserRepository;
import com.hackaton.grupo6.repository.TeamRepository;
import com.hackaton.grupo6.dto.TaskRequestDTO;
import com.hackaton.grupo6.dto.TaskResponseDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TaskResponseDTO createTask(TaskRequestDTO dto) {

        User manager = userRepository.findById(dto.idManager())
                .orElseThrow(() -> new RuntimeException("Manager não encontrado"));

        User employee = userRepository.findById(dto.idEmployee())
                .orElseThrow(() -> new RuntimeException("Employee não encontrado"));

        Team team = teamRepository.findById(dto.teamId())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

        Task task = new Task();
        task.setName(dto.name());
        task.setAbout(dto.about());
        task.setIdManager(manager);
        task.setIdEmployee(employee);
        task.setTaskPriority(dto.taskPriority());
        task.setTeam(team);
        task.setStatusTask(dto.statusTask());
        task.setStartDate(dto.startDate());
        task.setEndDate(dto.endDate());
        task.setEstimatedTime(dto.estimatedTime());
        task.setTimeSpent(dto.timeSpent());
        task.setComentary(new ArrayList<>()); // inicia vazio

        Task saved = taskRepository.save(task);

        return toResponseDTO(saved);
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO findById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        return toResponseDTO(task);
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getIdTask(),
                task.getName(),
                task.getAbout(),
                task.getIdManager().getIdUser(),   // pega o ID do manager
                task.getIdEmployee().getIdUser(), // pega o ID do employee
                task.getTaskPriority(),
                task.getTeam().getIdTeam(),           // pega o ID da equipe
                task.getStatusTask(),
                task.getStartDate(),
                task.getEndDate(),
                task.getEstimatedTime(),
                task.getTimeSpent(),
                task.getComentary()
        );
    }

}
