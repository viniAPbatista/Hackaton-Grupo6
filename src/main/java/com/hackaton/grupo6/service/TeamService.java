package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.TeamRequestDTO;
import com.hackaton.grupo6.dto.TeamResponseDTO;
import com.hackaton.grupo6.model.Team;
import com.hackaton.grupo6.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    public Team getTeamResponseDTO(UUID uuid){

        Team team = teamRepository.findById(uuid).orElseThrow(()->new RuntimeException("Time não encontrado."));

        return  team;
    }


    public List<Team> getAllTeams(){

        return teamRepository.findAll();
    }

    public TeamResponseDTO createTeam(TeamRequestDTO newTeam) {

        Team team = new Team();
        team.setName(newTeam.name());
        team.setUsers(new ArrayList<>());
        team.setTasks(new ArrayList<>());

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


}
