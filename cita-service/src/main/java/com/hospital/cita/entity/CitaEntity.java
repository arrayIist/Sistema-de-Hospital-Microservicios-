package com.hospital.cita.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("cita")
public class CitaEntity {

    @Id
    private Long id;

    @Column("paciente_id")
    @NotNull
    private Long pacienteId;

    @NotBlank
    private String especialidad;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column("fecha_hora")
    @NotNull
    private LocalDateTime fechaHora;

    @Column("profesional_codigo")
    private String profesionalCodigo;

    private String estado;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
}