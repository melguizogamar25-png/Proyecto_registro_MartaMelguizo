package com.salesianos.dam.Registro_jornada.service;

import com.salesianos.dam.Registro_jornada.exception.EmpleadoNoEncontradaException;
import com.salesianos.dam.Registro_jornada.exception.FichajeDuplicadoException;
import com.salesianos.dam.Registro_jornada.model.Empleado;
import com.salesianos.dam.Registro_jornada.model.Fichaje;
import com.salesianos.dam.Registro_jornada.model.TipoFichaje;
import com.salesianos.dam.Registro_jornada.repository.EmpleadoRepository;
import com.salesianos.dam.Registro_jornada.repository.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FichajeService {

    private final FichajeRepository fichajeRepository;
    private final EmpleadoRepository empleadoRepository;

    public Fichaje ficharEmpleado(Long empleadoId, TipoFichaje tipo) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EmpleadoNoEncontradaException(empleadoId));

        TipoFichaje ultimo = fichajeRepository
                .findFirstByEmpleadoIdOrderByMomentoDescIdDesc(empleadoId)
                .map(Fichaje::getTipo)
                .orElse(null);

        TipoFichaje nuevo = tipo != null ? tipo : (ultimo == TipoFichaje.ENTRADA ? TipoFichaje.SALIDA : TipoFichaje.ENTRADA);

        if (nuevo == ultimo) {
            throw new FichajeDuplicadoException(empleadoId, nuevo);
        }

        return fichajeRepository.save(
                Fichaje.builder()
                        .momento(LocalDateTime.now())
                        .tipo(nuevo)
                        .empleado(empleado)
                        .build());
    }

    public List<Fichaje> getFichajesByEmpleado(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new EmpleadoNoEncontradaException(empleadoId);
        }
        return fichajeRepository.findByEmpleadoIdOrderByMomentoAscIdAsc(empleadoId);
    }
}
