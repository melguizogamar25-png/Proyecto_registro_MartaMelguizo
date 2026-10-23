package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Fichaje;
import com.salesianos.dam.Registro_jornada.model.TipoFichaje;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "FichajeResponseDTO",
        description = "Registro de entrada o salida de un empleado"
)
public record FichajeResponseDto(
        @Schema(
                description = "Identificador único del fichaje",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Momento en el que se registró el fichaje",
                example = "2026-10-19T08:00:00"
        )
        LocalDateTime momento,

        @Schema(
                description = "Tipo de fichaje",
                example = "ENTRADA"
        )
        TipoFichaje tipo,

        @Schema(
                description = "Id del empleado que ha fichado",
                example = "1"
        )
        Long empleadoId) {

    public static FichajeResponseDto of(Fichaje f) {
        return new FichajeResponseDto(
                f.getId(),
                f.getMomento(),
                f.getTipo(),
                f.getEmpleado().getId()
        );
    }
}
