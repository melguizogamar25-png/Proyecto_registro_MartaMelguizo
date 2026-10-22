package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Empleado;
import com.salesianos.dam.Registro_jornada.validation.SalariosDentroDePresupuesto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(
        name = "EmpleadoRequestDTO",
        description = "Datos necesarios para crear o modificar un empleado"
)
@SalariosDentroDePresupuesto(message = "{empleadoRequestDTO.salario.SalariosDentroDePresupuesto}")
public record EmpleadoRequestDto(
        @Schema(
                description = "Nombre completo del empleado",
                example = "Ana Pérez García"
        )
        @NotBlank(message = "{empleadoRequestDTO.nombreCompleto.notBlank}")
        String nombreCompleto,

        @Schema(
                description = "Cargo del empleado",
                example = "Desarrolladora"
        )
        @NotBlank(message = "{empleadoRequestDTO.cargo.notBlank}")
        String cargo,

        @Schema(
                description = "Salario del empleado",
                example = "32000.00"
        )
        @NotNull(message = "{empleadoRequestDTO.salario.notNull}")
        @Positive(message = "{empleadoRequestDTO.salario.positive}")
        BigDecimal salario,

        @Schema(
                description = "Id del departamento al que pertenece (opcional)",
                example = "1"
        )
        Long departamentoId) {

    public Empleado to() {
        return Empleado.builder()
                .nombreCompleto(nombreCompleto)
                .cargo(cargo)
                .salario(salario)
                .build();
    }
}
