package com.hospital.paciente.controller;

import com.hospital.paciente.entity.PacienteEntity;
import com.hospital.paciente.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<PacienteEntity>> crear(@Valid @RequestBody Mono<PacienteEntity> pacienteMono) {
        return pacienteMono
            .flatMap(pacienteService::registrar)
            .map(guardado -> ResponseEntity.status(HttpStatus.CREATED).body(guardado));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PacienteEntity>> obtener(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
            .map(ResponseEntity::ok);
    }
}