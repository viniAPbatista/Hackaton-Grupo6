package com.hackaton.grupo6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "comentary")
public class UserComentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentary")
    private UUID id;

    @Column(name = "userComentary")
    private Text comentary;


}
