package com.hospital.cita.repository;

import com.hospital.cita.entity.CitaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CitaRepository extends ReactiveCrudRepository<CitaEntity, Long> {
}