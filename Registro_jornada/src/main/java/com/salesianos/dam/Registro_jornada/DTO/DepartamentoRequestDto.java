package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(
        name = "DepartamentoRequestDTO",
        description = "Datos necesarios para crear o modificar un departamento"
)
public record DepartamentoRequestDto(
        @Schema(
                description = "Nombre del departamento (único)",
                example = "Desarrollo"
        )
        @NotBlank(message = "{departamentoRequestDTO.nombre.notBlank}")
        String nombre,

        @Schema(
                description = "Presupuesto del departamento",
                example = "70000.00"
        )
        @NotNull(message = "{departamentoRequestDTO.presupuesto.notNull}")
        @PositiveOrZero(message = "{departamentoRequestDTO.presupuesto.positiveOrZero}")
        BigDecimal presupuesto) {

    public Departamento to() {
        return Departamento.builder()
                .nombre(nombre)
                .presupuesto(presupuesto)
                .build();
    }
}
