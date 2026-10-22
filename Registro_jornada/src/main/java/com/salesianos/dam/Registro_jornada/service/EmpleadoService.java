package com.salesianos.dam.Registro_jornada.service;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
import com.salesianos.dam.Registro_jornada.exception.DepartamentoNoEncontradaException;
import com.salesianos.dam.Registro_jornada.exception.EmpleadoNoEncontradaException;
import com.salesianos.dam.Registro_jornada.model.Departamento;
import com.salesianos.dam.Registro_jornada.model.Empleado;
import com.salesianos.dam.Registro_jornada.repository.DepartamentoRepository;
import com.salesianos.dam.Registro_jornada.repository.EmpleadoRepository;
import com.salesianos.dam.Registro_jornada.repository.FichajeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final FichajeRepository fichajeRepository;

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado getEmpleadosById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradaException(id));
    }

    public Empleado addEmpleado(EmpleadoRequestDto dto) {
        Empleado e = dto.to();

        if(dto.departamentoId() != null) {
            e.setDepartamento(getDepartamento(dto.departamentoId()));
        }
        return empleadoRepository.save(e);
    }

    public Empleado updateEmpleado(Long id, EmpleadoRequestDto dto) {
        return empleadoRepository.findById(id)
                .map(e -> {
                    e.setNombreCompleto(dto.nombreCompleto());
                    e.setCargo(dto.cargo());
                    e.setSalario(dto.salario());
                    return empleadoRepository.save(e);
                })
                .orElseThrow(() -> new EmpleadoNoEncontradaException(id));
    }

    @Transactional
    public void deleteEmpleado(Long id) {
        Empleado e = getEmpleadosById(id);
        fichajeRepository.deleteByEmpleadoId(id);
        empleadoRepository.delete(e);
    }

    public Empleado asignarDepartamento(Long empleadoId, Long deptoId) {
        Empleado e = getEmpleadosById(empleadoId);
        e.setDepartamento(getDepartamento(deptoId));
        return empleadoRepository.save(e);
    }

    private Departamento getDepartamento(Long deptoId) {
        return departamentoRepository.findById(deptoId)
                .orElseThrow(() -> new DepartamentoNoEncontradaException(deptoId));
    }
}
