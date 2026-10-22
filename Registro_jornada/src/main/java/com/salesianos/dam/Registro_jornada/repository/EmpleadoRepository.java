package com.salesianos.dam.Registro_jornada.repository;

import com.salesianos.dam.Registro_jornada.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query("select sum(e.salario) from Empleado e where e.departamento.id = :departamentoId")
    BigDecimal sumSalarioByDepartamentoId(@Param("departamentoId") Long departamentoId);
}
