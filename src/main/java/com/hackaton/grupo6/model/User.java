package com.hackaton.grupo6.model;


import com.hackaton.grupo6.enums.ActiveStatus;
import com.hackaton.grupo6.enums.UserEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    private String name;

    private  String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserEnum role;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @OneToOne(mappedBy = "user")
    private UserComentary userComentary;

    @OneToMany(mappedBy = "idEmployee")
    private List<Task> task;

    @OneToMany(mappedBy = "idManager")
    private List<Task> taskCreated;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;
}