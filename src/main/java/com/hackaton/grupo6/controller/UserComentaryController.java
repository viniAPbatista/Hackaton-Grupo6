package com.hackaton.grupo6.controller;

import com.hackaton.grupo6.dto.ComentaryRequestDTO;
import com.hackaton.grupo6.dto.ComentaryResponseDTO;
import com.hackaton.grupo6.service.UserComentaryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comentary")
@RequiredArgsConstructor
public class UserComentaryController {

    private final UserComentaryService userComentaryService;

    @PostMapping
    public ComentaryResponseDTO createComentary (@RequestBody ComentaryRequestDTO comentaryRequest) {

        return userComentaryService.createComnetary(comentaryRequest);
    }

    @GetMapping("/{id}")
    public ComentaryResponseDTO getComentary (@PathVariable UUID uuid) {
        return userComentaryService.getComentaryId(uuid);
    }
}
