package com.salesianos.dam.Registro_jornada.validation;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
import com.salesianos.dam.Registro_jornada.model.Departamento;
import com.salesianos.dam.Registro_jornada.repository.DepartamentoRepository;
import com.salesianos.dam.Registro_jornada.repository.EmpleadoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class SalariosDentroDePresupuestoValidator
        implements ConstraintValidator<SalariosDentroDePresupuesto, EmpleadoRequestDto> {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(EmpleadoRequestDto value, ConstraintValidatorContext context) {
        if (value == null || value.departamentoId() == null || value.salario() == null) {
            return true;
        }

        Departamento departamento = departamentoRepository
                .findById(value.departamentoId())
                .orElse(null);

        if (departamento == null) {
            return true;
        }

        BigDecimal salariosActuales = empleadoRepository
                .sumSalarioByDepartamentoId(departamento.getId());

        if (salariosActuales == null) {
            salariosActuales = BigDecimal.ZERO;
        }

        boolean valido = salariosActuales.add(value.salario())
                .compareTo(departamento.getPresupuesto()) <= 0;

        if (!valido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("salario")
                    .addConstraintViolation();
        }

        return valido;
    }

}
