package com.hackaton.grupo6.service;

import com.hackaton.grupo6.dto.ComentaryRequestDTO;
import com.hackaton.grupo6.dto.ComentaryResponseDTO;
import com.hackaton.grupo6.model.Task;
import com.hackaton.grupo6.model.User;
import com.hackaton.grupo6.model.UserComentary;
import com.hackaton.grupo6.repository.TaskRepository;
import com.hackaton.grupo6.repository.UserComentaryRepository;
import com.hackaton.grupo6.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserComentaryService {

    private final UserComentaryRepository userComentaryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ComentaryResponseDTO createComnetary (@RequestBody ComentaryRequestDTO comentaryRequest) {

        UserComentary newComentary = new UserComentary();
        newComentary.setComentary(comentaryRequest.comentary());

        Task task = taskRepository.findById(comentaryRequest.task()).orElseThrow(
                () -> new RuntimeException("Task não encontrada!")
        );

        newComentary.setTask(task);

        User user = userRepository.findById(comentaryRequest.user()).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado!")
        );

        newComentary.setUser(user);

        userComentaryRepository.save(newComentary);

        return new ComentaryResponseDTO(
                newComentary.getIdComentario(),
                newComentary.getComentary()
        );
    }

    public ComentaryResponseDTO getComentaryId(UUID id) {

        UserComentary comentary = userComentaryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Comentario não encontrado!")
        );

        return new ComentaryResponseDTO(
                comentary.getIdComentario(),
                comentary.getComentary()
        );
    }
}
