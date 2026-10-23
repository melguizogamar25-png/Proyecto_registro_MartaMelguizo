package com.salesianos.dam.Registro_jornada.exception;

import com.salesianos.dam.Registro_jornada.model.TipoFichaje;

public class FichajeDuplicadoException extends RuntimeException {

    public FichajeDuplicadoException(Long empleadoId, TipoFichaje tipo) {
        super("El empleado con id %d ya tiene como último fichaje una %s".formatted(empleadoId, tipo));
    }
}
