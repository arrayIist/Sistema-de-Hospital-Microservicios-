package com.hospital.paciente.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("paciente")
public class PacienteEntity {
	@Id
    private Long id;

    @NotBlank
    @Column("tipo_documento")
    private String tipoDocumento;

    @NotBlank
    @Column("numero_documento")
    private String numeroDocumento;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @JsonFormat(pattern = "yyyy-MM-dd") 
    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String telefono;
    private String correo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    private String estado;
}
