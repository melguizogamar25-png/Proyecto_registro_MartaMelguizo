package com.salesianos.dam.Registro_jornada.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SalariosDentroDePresupuestoValidator.class)
@Documented

public @interface SalariosDentroDePresupuesto {

    String message() default "La suma de los salarios del departamento supera su presupuesto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
