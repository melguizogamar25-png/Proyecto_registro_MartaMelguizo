package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Departamento;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DepartamentoRequestDto(
        String nombre, BigDecimal presupuesto) {

    public Departamento to() {
        return Departamento.builder()
                .nombre(nombre)
                .presupuesto(presupuesto)
                .build();
    }
}
