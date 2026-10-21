package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Empleado;

import java.math.BigDecimal;

public record EmpleadoResponseDto(
        Long id, String nombreCompleto, String cargo, BigDecimal salario, String departamentoNombre) {

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
