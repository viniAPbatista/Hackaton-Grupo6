package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.*;
import com.hackaton.grupo6.model.Team;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.repository.TeamRepository;
import com.hackaton.grupo6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamResponseDTO getTeamId(UUID uuid){

        Team team = teamRepository.findById(uuid).orElseThrow(()->new RuntimeException("Time não encontrado."));

        return new TeamResponseDTO(
                team.getId(),
                team.getName(),
                team.getUsers(),
                team.getTasks()
        );
    }


    public List<Team> getAllTeams(){

        return teamRepository.findAll();
    }

    public TeamResponseDTO createTeam(TeamRequestDTO newTeam) {

        Team team = new Team();
        team.setName(newTeam.name());

        teamRepository.save(team);

        return new TeamResponseDTO(
                team.getId(),
                team.getName(),
                team.getUsers(),
                team.getTasks()
        );
    }

    public Team teamUpdate(UUID id , Team newTeam){

        Team team= teamRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Time não encontrado."));

        team.setName(newTeam.getName());

        return teamRepository.save(team);
    }

    public TeamResponseDTO addUser(TeamAddUserRequestDTO dto) {
        Team team = teamRepository.findById(dto.idTeam())
                .orElseThrow(() -> new RuntimeException("Time não encontrado."));

        User user = userRepository.findById(dto.idUser())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        user.setTeam(team);
        team.getUsers().add(user);

        userRepository.save(user);
        teamRepository.save(team);

        return new TeamResponseDTO(
                team.getId(),
                team.getName(),
                team.getUsers(),
                team.getTasks()
        );
    }

    public List<RegisterUserResponseDTO> getUsersByTeam(UUID idTeam) {
        return userRepository.findByTeam_Id(idTeam);
    }
}
