package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Empleado;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(
        name = "EmpleadoResponseDTO",
        description = "Detalle de un empleado"
)
public record EmpleadoResponseDto(
        @Schema(
                description = "Identificador único del empleado",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre completo del empleado",
                example = "Ana Pérez García"
        )
        String nombreCompleto,

        @Schema(
                description = "Cargo del empleado",
                example = "Desarrolladora"
        )
        String cargo,

        @Schema(
                description = "Salario del empleado",
                example = "32000.00"
        )
        BigDecimal salario,

        @Schema(
                description = "Nombre del departamento al que pertenece (null si no tiene)",
                example = "Desarrollo"
        )
        String departamentoNombre) {

    public static EmpleadoResponseDto of(Empleado e) {
        return new EmpleadoResponseDto(
                e.getId(),
                e.getNombreCompleto(),
                e.getCargo(),
                e.getSalario(),
                e.getDepartamento() != null ? e.getDepartamento().getNombre() : null
        );
    }
}
