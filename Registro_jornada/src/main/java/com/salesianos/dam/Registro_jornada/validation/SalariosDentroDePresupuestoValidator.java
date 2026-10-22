package com.salesianos.dam.Registro_jornada.validation;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
import com.salesianos.dam.Registro_jornada.repository.DepartamentoRepository;
import com.salesianos.dam.Registro_jornada.repository.EmpleadoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class SalariosDentroDePresupuestoValidator
    implements ConstraintValidator<SalariosDentroDePresupuesto, EmpleadoRequestDto> {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(EmpleadoRequestDto value, ConstraintValidatorContext context) {
        if (value == null || value.departamentoId() {

        }
    }

}
