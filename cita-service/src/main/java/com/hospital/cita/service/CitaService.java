package com.hospital.cita.service;

import com.hospital.cita.cliente.PacienteClient;
import com.hospital.cita.config.RabbitConfig;
import com.hospital.cita.entity.CitaEntity;
import com.hospital.cita.exception.TransicionNoPermitidaException;
import com.hospital.cita.repository.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteClient pacienteClient;
    private final RabbitTemplate rabbitTemplate;

    private static final Map<String, java.util.List<String>> TRANSICIONES = Map.of(
            "Pendiente", java.util.List.of("Confirmada", "Cancelada"),
            "Confirmada", java.util.List.of("Atendida", "Cancelada", "Ausente")
    );

    public Mono<CitaEntity> crear(CitaEntity cita) {
        return pacienteClient.existePaciente(cita.getPacienteId())
            .flatMap(existe -> {
                if (!existe) {
                    return Mono.error(new IllegalArgumentException("Paciente no existe"));
                }
                cita.setEstado("Pendiente");
                cita.setFechaCreacion(LocalDateTime.now());
                return citaRepository.save(cita);
            });
    }

    public Mono<CitaEntity> cambiarEstado(Long id, String nuevoEstado) {
        return citaRepository.findById(id)
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Cita no encontrada")))
            .flatMap(cita -> {
                if (!esTransicionValida(cita.getEstado(), nuevoEstado)) {
                    return Mono.error(new TransicionNoPermitidaException(
                        "No se puede pasar de " + cita.getEstado() + " a " + nuevoEstado));
                }
                cita.setEstado(nuevoEstado);
                return citaRepository.save(cita);
            })
            .doOnNext(cita -> {
                if ("Confirmada".equals(nuevoEstado)) {
                    publicarEventoCitaConfirmada(cita);
                }
            });
    }

    private boolean esTransicionValida(String estadoActual, String nuevoEstado) {
        return TRANSICIONES.getOrDefault(estadoActual, java.util.List.of()).contains(nuevoEstado);
    }

    private void publicarEventoCitaConfirmada(CitaEntity cita) {
        Map<String, Object> evento = Map.of(
                "citaId", cita.getId(),
                "pacienteId", cita.getPacienteId(),
                "estado", "Confirmada",
                "fecha", LocalDateTime.now().toString()
        );
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY_CITA_CONFIRMADA, evento);
        System.out.println("Evento publicado: Cita " + cita.getId() + " Confirmada");
    }

    public Mono<CitaEntity> buscarPorId(Long id) {
        return citaRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cita no encontrada")));
    }
}