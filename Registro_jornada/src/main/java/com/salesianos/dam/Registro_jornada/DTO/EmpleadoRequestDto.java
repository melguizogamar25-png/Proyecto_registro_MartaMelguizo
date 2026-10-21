package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Empleado;

import java.math.BigDecimal;

public record EmpleadoRequestDto(
        String nombreCompleto, String cargo, BigDecimal salario, Long departamentoId) {

    public Empleado to() {
        return Empleado.builder()
                .nombreCompleto(nombreCompleto)
                .cargo(cargo)
                .salario(salario)
                .build();
    }
}
