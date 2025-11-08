package com.hackaton.grupo6.model;

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
    private UUID idTeam;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Task> tasks = new ArrayList<>();

}