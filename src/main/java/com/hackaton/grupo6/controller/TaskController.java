package com.hackaton.grupo6.controller;

import com.hackaton.grupo6.dto.*;
import com.hackaton.grupo6.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTask(@PathVariable UUID id) {
        return taskService.getTask(id);
    }

    @PatchMapping("/addTimeSpent")
    public TaskResponseDTO addTimeSpent(@RequestBody AddTimeSpentDTO dto) {
        return taskService.addTimeSpent(dto);
    }

    @PatchMapping("/trasferTask")
    public TaskResponseDTO trasnferTask(@RequestBody TrasnferTaskRequestDTO dto) {
        return taskService.transferTask(dto);
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TaskResponseDTO> finalizeTask(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.finalizeTask(id));
    }

    @GetMapping("/taskEmployee/{id}")
    public List<TaskResponseDTO> getTasksEmployee(@PathVariable UUID id) {
        return taskService.getTasksByEmployeeId(id);
    }

    @GetMapping("/taskManager/{id}")
    public List<TaskResponseDTO> getTasksManager(@PathVariable UUID id) {
        return taskService.getTasksByManager(id);
    }

    @GetMapping("/taskTeam/{id}")
    public List<TaskResponseDTO> getTasksTeam(@PathVariable UUID id) {
        return taskService.getTasksTeam(id);
    }

    @PatchMapping("/changeStatus")
    public TaskResponseDTO changeStatusTask(@RequestBody ChangeStatusTaskRequestDTO dto) {
        return taskService.changeStatusTask(dto);
    }

    @PatchMapping("/userGetTask")
    public TaskResponseDTO userGetTask(@RequestBody UserGetTaskRequest dto) {
        return taskService.userGetTask(dto);
    }
}


