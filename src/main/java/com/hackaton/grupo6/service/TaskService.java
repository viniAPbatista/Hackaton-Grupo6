package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.AddTimeSpentDTO;
import com.hackaton.grupo6.dto.TaskRequestDTO;
import com.hackaton.grupo6.dto.TaskResponseDTO;
import com.hackaton.grupo6.dto.TrasnferTaskRequestDTO;
import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.Team;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.TaskRepository;
import com.hackaton.grupo6.repository.TeamRepository;
import com.hackaton.grupo6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;
import com.hackaton.grupo6.enums.StatusTask;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequest) {

        Task newTask = new Task();
        newTask.setName(taskRequest.name());
        newTask.setAbout(taskRequest.about());

        User manager = userRepository.findById(taskRequest.idManager()).orElseThrow(
                () -> new RuntimeException("Gerente não encontrado!")
        );

        newTask.setIdManager(manager);

        User employee = userRepository.findById(taskRequest.idEmployee()).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado!")
        );

        newTask.setIdEmployee(employee);
        newTask.setTaskPriority(taskRequest.taskPriority());

        Team team = teamRepository.findById(taskRequest.teamId()).orElseThrow(
                () -> new RuntimeException("Time não encontrado!")
        );

        newTask.setTeam(team);
        newTask.setStatusTask(taskRequest.statusTask());
        newTask.setStartDate(taskRequest.startDate());
        newTask.setEndDate(taskRequest.endDate());
        newTask.setEstimatedTime(taskRequest.estimatedTime());
        newTask.setTimeSpent(taskRequest.timeSpent());

        taskRepository.save(newTask);

        return new TaskResponseDTO (
                newTask.getIdTask(),
                newTask.getName(),
                newTask.getAbout(),
                newTask.getIdManager().getName(),
                newTask.getIdEmployee().getName(),
                newTask.getTaskPriority(),
                newTask.getTeam().getName(),
                newTask.getStatusTask(),
                newTask.getStartDate(),
                newTask.getEndDate(),
                newTask.getEstimatedTime(),
                newTask.getTimeSpent(),
                newTask.getComentary()
        );
    }

    public TaskResponseDTO getTask(UUID id) {

        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task não encontrada!")
        );

        return new TaskResponseDTO (
                task.getIdTask(),
                task.getName(),
                task.getAbout(),
                task.getIdManager().getName(),
                task.getIdEmployee().getName(),
                task.getTaskPriority(),
                task.getTeam().getName(),
                task.getStatusTask(),
                task.getStartDate(),
                task.getEndDate(),
                task.getEstimatedTime(),
                task.getTimeSpent(),
                task.getComentary()
        );

    }

    public TaskResponseDTO addTimeSpent(AddTimeSpentDTO dto) {

        Task task =  taskRepository.findById(dto.idTask()).orElseThrow(
                () -> new RuntimeException("Task não encontrada!")
        );

        task.setTimeSpent(dto.timeSpent());
        taskRepository.save(task);

        return new TaskResponseDTO(
                task.getIdTask(),
                task.getName(),
                task.getAbout(),
                task.getIdManager().getName(),
                task.getIdEmployee().getName(),
                task.getTaskPriority(),
                task.getTeam().getName(),
                task.getStatusTask(),
                task.getStartDate(),
                task.getEndDate(),
                task.getEstimatedTime(),
                task.getTimeSpent(),
                task.getComentary()
        );
    }

    public TaskResponseDTO finalizeTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada!")
        );

        task.setStatusTask(StatusTask.FINALIZADA); // Enum, não String
        task.setEndDate(LocalDateTime.now()); // LocalDateTime, não LocalDate
        taskRepository.save(task);

        return new TaskResponseDTO(
                task.getIdTask(),
                task.getName(),
                task.getAbout(),
                task.getIdManager().getName(),
                task.getIdEmployee().getName(),
                task.getTaskPriority(),
                task.getTeam().getName(),
                task.getStatusTask(),
                task.getStartDate(),
                task.getEndDate(),
                task.getEstimatedTime(),
                task.getTimeSpent(),
                task.getComentary()
        );
    }

    public TaskResponseDTO transferTask(TrasnferTaskRequestDTO dto) {

        Task task = taskRepository.findById(dto.taskId()).orElseThrow(
                () -> new RuntimeException("Task não encontrada!")
        );

        User user = userRepository.findById(dto.idNewUser()).orElseThrow(
                () -> new RuntimeException("usuario não encontrado!")
        );

        task.setIdEmployee(user);

        taskRepository.save(task);

        return new TaskResponseDTO (
                task.getIdTask(),
                task.getName(),
                task.getAbout(),
                task.getIdManager().getName(),
                task.getIdEmployee().getName(),
                task.getTaskPriority(),
                task.getTeam().getName(),
                task.getStatusTask(),
                task.getStartDate(),
                task.getEndDate(),
                task.getEstimatedTime(),
                task.getTimeSpent(),
                task.getComentary()
        );
    }

    public List<TaskResponseDTO> getTasksByEmployeeId(UUID userId) {
        List<Task> tasks = taskRepository.findByIdEmployee_IdUser(userId);

        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getIdTask(),
                        task.getName(),
                        task.getAbout(),
                        task.getIdManager().getName(),
                        task.getIdEmployee().getName(),
                        task.getTaskPriority(),
                        task.getTeam().getName(),
                        task.getStatusTask(),
                        task.getStartDate(),
                        task.getEndDate(),
                        task.getEstimatedTime(),
                        task.getTimeSpent(),
                        task.getComentary()
                ))
                .toList();
    }

    public List<TaskResponseDTO> getTasksByManager(UUID userId) {
        List<Task> tasks = taskRepository.findByIdManager_IdUser(userId);

        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getIdTask(),
                        task.getName(),
                        task.getAbout(),
                        task.getIdManager().getName(),
                        task.getIdEmployee().getName(),
                        task.getTaskPriority(),
                        task.getTeam().getName(),
                        task.getStatusTask(),
                        task.getStartDate(),
                        task.getEndDate(),
                        task.getEstimatedTime(),
                        task.getTimeSpent(),
                        task.getComentary()
                ))
                .toList();
    }

    public List<TaskResponseDTO> getTasksTeam(UUID id) {
        List<Task> tasks = taskRepository.findByTeam_Id(id);

        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getIdTask(),
                        task.getName(),
                        task.getAbout(),
                        task.getIdManager().getName(),
                        task.getIdEmployee().getName(),
                        task.getTaskPriority(),
                        task.getTeam().getName(),
                        task.getStatusTask(),
                        task.getStartDate(),
                        task.getEndDate(),
                        task.getEstimatedTime(),
                        task.getTimeSpent(),
                        task.getComentary()
                ))
                .toList();
    }
}
