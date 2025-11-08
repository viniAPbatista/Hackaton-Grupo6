package com.hackaton.grupo6.controller;

import com.hackaton.grupo6.dto.AddTimeSpentDTO;
import com.hackaton.grupo6.dto.TaskRequestDTO;
import com.hackaton.grupo6.dto.TaskResponseDTO;
import com.hackaton.grupo6.dto.TrasnferTaskRequestDTO;
import com.hackaton.grupo6.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TaskResponseDTO> finalizeTask(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.finalizeTask(id));
    }
}

