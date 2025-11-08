package com.hackaton.grupo6.controller;


import com.hackaton.grupo6.dto.TeamAddUserRequestDTO;
import com.hackaton.grupo6.dto.TeamRequestDTO;
import com.hackaton.grupo6.dto.TeamResponseDTO;
import com.hackaton.grupo6.model.Team;
import com.hackaton.grupo6.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/team")
@RestController
public class TimeController {

    private final TeamService teamService;

    public TimeController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{uuid}")
    public TeamResponseDTO getTeam(@PathVariable UUID uuid){

        return teamService.getTeamId(uuid);
    }

    @PostMapping()
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO teamRequestDTO){

        TeamResponseDTO createdTeam = teamService.createTeam(teamRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTeam);
    }

    @GetMapping()
    public List<Team> getAllTeams() {

        return teamService.getAllTeams();
    }

    @PatchMapping("/addUser")
    public TeamResponseDTO addUser(@RequestBody TeamAddUserRequestDTO dto) {
        return teamService.addUser(dto);
    }
}
