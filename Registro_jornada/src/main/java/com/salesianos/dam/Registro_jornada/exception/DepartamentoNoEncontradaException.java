package com.salesianos.dam.Registro_jornada.exception;

public class DepartamentoNoEncontradaException extends EntityNotFoundException{

    public DepartamentoNoEncontradaException(Long id) {
        super("Departamento no encontrado con id " + id);
    }
}
