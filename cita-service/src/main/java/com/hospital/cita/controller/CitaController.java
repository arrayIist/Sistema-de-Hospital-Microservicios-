package com.hospital.cita.controller;

import com.hospital.cita.entity.CitaEntity;
import com.hospital.cita.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<CitaEntity>> crear(@Valid @RequestBody Mono<CitaEntity> citaMono) {
        return citaMono
            .flatMap(citaService::crear)
            .map(creada -> ResponseEntity.status(HttpStatus.CREATED).body(creada));
    }

    @PatchMapping("/{id}/estado")
    public Mono<ResponseEntity<CitaEntity>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        return citaService.cambiarEstado(id, nuevoEstado)
            .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CitaEntity>> obtener(@PathVariable Long id) {
        return citaService.buscarPorId(id)
            .map(ResponseEntity::ok);
    }
}
