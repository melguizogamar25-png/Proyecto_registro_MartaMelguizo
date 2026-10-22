package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.TipoFichaje;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "FichajeRequestDTO",
        description = "Tipo de fichaje a registrar. Si se omite, se alterna automáticamente con el último fichaje"
)
public record FichajeRequestDto(
        @Schema(
                description = "Tipo de fichaje",
                example = "ENTRADA"
        )
        TipoFichaje tipo) {

}
