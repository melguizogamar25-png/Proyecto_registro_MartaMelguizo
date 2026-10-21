package com.salesianos.dam.Registro_jornada.service;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
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
                .orElse(null); //Sustituye
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
                .orElse(null);
    }
    //Borrar


    private Departamento getDepartamento(Long deptoId) {
        return departamentoRepository.findById(deptoId)
                .orElse(null); //Sustitu
    }
}
