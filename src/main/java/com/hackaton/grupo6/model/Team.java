package com.hackaton.grupo6.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //para equals e hashCode, incluindo apenas o campo id.
@NoArgsConstructor //construtor sem argumentos
@AllArgsConstructor //construtor com todos os argumentos

@Table(name = "tb_team")
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;






}