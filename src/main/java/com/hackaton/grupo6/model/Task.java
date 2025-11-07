package com.hackaton.grupo6.model;

import com.hackaton.grupo6.enums.StatusTask;
import com.hackaton.grupo6.enums.TaskPriority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTask;

    private String name;

    private String about;

    private UUID idManager;

    private UUID idEmployee;

    private TaskPriority taskPriority;

    private UUID team;

    private StatusTask statusTask;

    private LocalDateTime endDate;

    private LocalDateTime startDate;

    private Double estimatedTime;

    private Double timeSpent;
}
