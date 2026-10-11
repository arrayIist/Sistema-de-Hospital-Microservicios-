package com.hospital.paciente.service;

import com.hospital.paciente.entity.PacienteEntity;
import com.hospital.paciente.exception.RecursoDuplicadoException;
import com.hospital.paciente.exception.RecursoNoEncontradoException;
import com.hospital.paciente.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public Mono<PacienteEntity> registrar(PacienteEntity paciente) {
        return pacienteRepository.existsByTipoDocumentoAndNumeroDocumento(
                paciente.getTipoDocumento(), paciente.getNumeroDocumento())
            .flatMap(existe -> {
                if (existe) {
                    return Mono.error(new RecursoDuplicadoException(
                        "Paciente con documento " + paciente.getNumeroDocumento() + " ya existe"));
                }
                paciente.setFechaRegistro(LocalDateTime.now());
                paciente.setEstado("Activo");
                return pacienteRepository.save(paciente);
            });
    }

    public Mono<PacienteEntity> buscarPorId(Long id) {
        return pacienteRepository.findById(id)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Paciente no encontrado")));
    }
}