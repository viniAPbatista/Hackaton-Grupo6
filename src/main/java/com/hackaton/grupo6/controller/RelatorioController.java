package com.hackaton.grupo6.controller;
import com.hackaton.grupo6.dto.TaskResponseDTO;
import com.hackaton.grupo6.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final TaskService taskService;

    @GetMapping("/user/{id}")
    public List<TaskResponseDTO> getUserTaskHistory(@PathVariable String id) {
        return taskService.getTasksHistoryByUserIdentifier(id);
    }
}
