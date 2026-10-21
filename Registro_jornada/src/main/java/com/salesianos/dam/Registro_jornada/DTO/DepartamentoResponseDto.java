package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Departamento;

import java.math.BigDecimal;
import java.util.List;

public record DepartamentoResponseDto(
        Long id, String nombre, BigDecimal presupuesto,
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
