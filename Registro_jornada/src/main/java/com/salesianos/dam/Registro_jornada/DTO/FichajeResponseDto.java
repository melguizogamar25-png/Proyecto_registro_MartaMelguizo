package com.salesianos.dam.Registro_jornada.DTO;

import com.salesianos.dam.Registro_jornada.model.Fichaje;
import com.salesianos.dam.Registro_jornada.model.TipoFichaje;

import java.time.LocalDateTime;

public record FichajeResponseDto(
        Long id, LocalDateTime momento, TipoFichaje tipo, Long empleadoId) {

    public static FichajeResponseDto of (Fichaje f) {
        return new FichajeResponseDto(
               f.getId(),
               f.getMomento(),
               f.getTipo(),
               f.getEmpleado().getId()
        );
    }
}
