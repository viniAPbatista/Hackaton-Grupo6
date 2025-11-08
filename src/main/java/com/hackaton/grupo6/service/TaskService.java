package com.hackaton.grupo6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.TaskRepository;
import com.hackaton.grupo6.repository.UserRepository;
import com.hackaton.grupo6.dto.TaskRequestDTO;
import com.hackaton.grupo6.dto.TaskResponseDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponseDTO createTask(TaskRequestDTO dto) {

        User manager = userRepository.findById(dto.idManager())
                .orElseThrow(() -> new RuntimeException("Manager não encontrado"));

        User employee = userRepository.findById(dto.idEmployee())
                .orElseThrow(() -> new RuntimeException("Employee não encontrado"));

        Task task = new Task();
        task.setName(dto.name());
        task.setAbout(dto.about());
        task.setTeam(dto.team());
        task.setIdManager(manager.getId());
        task.setIdEmployee(employee.getId());
        task.setStartDate(dto.startDate());
        task.setEndDate(dto.endDate());
        task.setTimeSpent(dto.timeSpent());
        task.setTaskPriority(dto.taskPriority());
        task.setStatusTask(dto.statusTask());
        task.setEstimatedTime(dto.estimatedTime());

        Task saved = taskRepository.save(task);

        return new TaskResponseDTO(
                saved.getIdTask(),
                saved.getName(),
                saved.getIdTask(),
                saved.getAbout(),
                saved.getTeam(),
                saved.getIdManager(),
                saved.getIdEmployee(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getTimeSpent(),
                saved.getTaskPriority(),
                saved.getStatusTask(),
                saved.getEstimatedTime()
        );
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        return toResponseDTO(task);
    }
}
