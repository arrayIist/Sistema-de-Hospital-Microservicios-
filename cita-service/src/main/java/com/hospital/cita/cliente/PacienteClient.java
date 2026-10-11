package com.hospital.cita.cliente;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PacienteClient {

	private final WebClient webClient;

    public PacienteClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8081") 
                .build();
    }

    public Mono<Boolean> existePaciente(Long pacienteId) {
        return webClient
        		.get()
                .uri("/api/pacientes/{id}", pacienteId)
                .retrieve()
                .toBodilessEntity()
                .map(resp -> resp.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false);
    }
}