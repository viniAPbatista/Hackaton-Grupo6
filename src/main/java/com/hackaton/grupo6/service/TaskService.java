package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.*;
import com.hackaton.grupo6.enums.UserEnum;
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

        User user = userRepository.findById(taskRequest.idManager()).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado!")
        );

        if (user.getRole() != UserEnum.MANAGER && user.getRole() != UserEnum.ADMIN) {
            throw new RuntimeException("Acesso negado: apenas MANAGER ou ADMIN podem acessar este recurso.");
        }


        Task newTask = new Task();
        newTask.setName(taskRequest.name());
        newTask.setAbout(taskRequest.about());

        User manager = userRepository.findById(taskRequest.idManager()).orElseThrow(
                () -> new RuntimeException("Gerente não encontrado!")
        );

        newTask.setIdManager(manager);

        User employee = null;
        if (taskRequest.idEmployee() != null) {
            employee = userRepository.findById(taskRequest.idEmployee())
                    .orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));
        }

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
                newTask.getIdEmployee() != null ? newTask.getIdEmployee().getName() : null,
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

        if (user.getRole() != UserEnum.MANAGER && user.getRole() != UserEnum.ADMIN) {
            throw new RuntimeException("Acesso negado: apenas MANAGER ou ADMIN podem acessar este recurso.");
        }

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
        List<Task> tasks = taskRepository.findByIdEmployee_IdUserOrderByStartDateDesc(userId);

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

    public List<TaskResponseDTO> getTasksHistoryByUserIdentifier(String identifier) {
        UUID userId;
        try {
            userId = UUID.fromString(identifier);
        } catch (IllegalArgumentException ex) {
            try {
                int index = Integer.parseInt(identifier);
                if (index < 1) {
                    throw new RuntimeException("ID numérico deve ser >= 1");
                }

                var users = userRepository.findAll();
                if (index > users.size()) {
                    throw new RuntimeException("Usuário numérico não encontrado");
                }
                userId = users.get(index - 1).getIdUser();
            } catch (NumberFormatException nf) {
                throw new RuntimeException("Identificador de usuário inválido. Use UUID ou número.");
            }
        }

        return getTasksByEmployeeId(userId);
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


    public TaskResponseDTO changeStatusTask(ChangeStatusTaskRequestDTO dto){

        Task task = taskRepository.findById(dto.id()).orElseThrow(
                () -> new RuntimeException("Task não encontrada!")
        );

        task.setStatusTask(dto.status());

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

    public TaskResponseDTO userGetTask(UserGetTaskRequest dto) {

        Task task = taskRepository.findById(dto.idUser()).orElseThrow(
                () -> new RuntimeException("Task não encontrada!")
        );

        User user = userRepository.findById(dto.idUser()).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado!")
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

    // Lista tarefas pendentes
    public List<TaskResponseDTO> getPendingTasks() {
        List<Task> tasks = taskRepository.findByStatusTaskOrderByStartDateDesc(StatusTask.PENDENTE);

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

    public List<TaskResponseDTO>getTaskConcluid(){
        List<Task> tasks = taskRepository.findByStatusTaskOrderByStartDateDesc(StatusTask.FINALIZADA);

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

    // Lista tarefas em andamento
    public List<TaskResponseDTO> getInProgressTasks() {
        List<Task> tasks = taskRepository.findByStatusTaskOrderByStartDateDesc(StatusTask.EM_ANDAMENTO);

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
