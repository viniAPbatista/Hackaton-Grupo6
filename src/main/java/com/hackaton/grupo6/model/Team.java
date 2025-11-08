package com.hackaton.grupo6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor //construtor sem argumentos
@AllArgsConstructor //construtor com todos os argumentos
@Getter
@Setter
@Table(name = "team")
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @JsonManagedReference("team-users")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    @JsonManagedReference("team-tasks")
    private List<Task> tasks = new ArrayList<>();

}