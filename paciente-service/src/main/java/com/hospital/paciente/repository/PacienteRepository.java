package com.hospital.paciente.repository;

import com.hospital.paciente.entity.PacienteEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PacienteRepository extends ReactiveCrudRepository<PacienteEntity, Long> {

    Mono<PacienteEntity> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
           "FROM paciente WHERE tipo_documento = :tipo AND numero_documento = :numero")
    Mono<Boolean> existsByTipoDocumentoAndNumeroDocumento(String tipo, String numero);
}