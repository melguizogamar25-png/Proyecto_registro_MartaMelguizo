package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Departamento;

import java.math.BigDecimal;

public record DepartamentoResponseDto(
        Long id, String nombre, BigDecimal presupuesto) {

    public static DepartamentoResponseDto of (Departamento d) {
        return new DepartamentoResponseDto(
                d.getId(),
                d.getNombre(),
                d.getPresupuesto()
        );
    }
}
