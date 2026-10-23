package com.salesianos.dam.Registro_jornada.exception;

public class EmpleadoNoEncontradaException extends EntityNotFoundException {

    public EmpleadoNoEncontradaException(Long id) {
        super("Empleado no encontrado con id " + id);
    }
}
