package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Empleado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EmpleadoRequestDto(
        @NotBlank(message = "{empleadoRequestDTO.nombreCompleto.notBlank}")
        String nombreCompleto,
        @NotBlank(message = "{empleadoRequestDTO.cargo.notBlank}")
        String cargo,
        @NotNull(message = "{empleadoRequestDTO.salario.notNull}")
        @Positive(message = "{empleadoRequestDTO.salario.positive}")
        BigDecimal salario,
        Long departamentoId) {

    public Empleado to() {
        return Empleado.builder()
                .nombreCompleto(nombreCompleto)
                .cargo(cargo)
                .salario(salario)
                .build();
    }
}
