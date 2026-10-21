package com.salesianos.dam.Registro_jornada.repository;

import com.salesianos.dam.Registro_jornada.model.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {

    void deleteByEmpleadoId(Long empleadoId);

    List<Fichaje> findByEmpleadoIdOrderByMomentoAscIdAsc(Long empleadoId);

    Optional<Fichaje> findFirstByEmpleadoIdOrderByMomentoDescIdDesc(Long empleadoId);
}
