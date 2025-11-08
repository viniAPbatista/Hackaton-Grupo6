package com.hackaton.grupo6.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hackaton.grupo6.enums.StatusTask;
import com.hackaton.grupo6.enums.TaskPriority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "idManager")
    @JsonBackReference("manager-tasks")
    private User idManager;

    @ManyToOne
    @JoinColumn(name = "idEmployee",nullable = true)
    @JsonBackReference("employee-tasks")
    private User idEmployee;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonBackReference("team-tasks")
    private Team team;

    @Enumerated(EnumType.STRING)
    private StatusTask statusTask;

    private LocalDateTime endDate;

    private LocalDateTime startDate;

    private Double estimatedTime;

    private Double timeSpent;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference("task-comentary")
    private List<UserComentary> comentary;
}
