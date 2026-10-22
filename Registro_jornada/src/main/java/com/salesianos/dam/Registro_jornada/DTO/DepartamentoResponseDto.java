package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(
        name = "DepartamentoResponseDTO",
        description = "Detalle de un departamento"
)
public record DepartamentoResponseDto(
        @Schema(
                description = "Identificador único del departamento",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre del departamento",
                example = "Desarrollo"
        )
        String nombre,

        @Schema(
                description = "Presupuesto del departamento",
                example = "70000.00"
        )
        BigDecimal presupuesto,

        @Schema(
                description = "Empleados que pertenecen al departamento"
        )
        List<EmpleadoResponseDto> empleados) {

    public static DepartamentoResponseDto of (Departamento d) {
        return new DepartamentoResponseDto(
                d.getId(),
                d.getNombre(),
                d.getPresupuesto(),
                d.getEmpleados().stream().map(EmpleadoResponseDto::of).toList()
        );
    }
}
