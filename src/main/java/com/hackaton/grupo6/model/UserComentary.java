package com.hackaton.grupo6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "comentary")
public class UserComentary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_comentary")
    private UUID idComentario;

    @Column(name = "userComentary")
    private String comentary;


}
