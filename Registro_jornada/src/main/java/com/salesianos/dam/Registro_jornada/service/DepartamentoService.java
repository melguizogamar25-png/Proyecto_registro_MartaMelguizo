package com.salesianos.dam.Registro_jornada.service;

import com.salesianos.dam.Registro_jornada.DTO.DepartamentoRequestDto;
import com.salesianos.dam.Registro_jornada.model.Departamento;
import com.salesianos.dam.Registro_jornada.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id)
                .orElse(null); //Sustituyendo a la excepcion
    }
    
    public Departamento addDepartamento(DepartamentoRequestDto dto) {
        return departamentoRepository.save(dto.to());
    }

    public Departamento updateDepartamento(Long id, DepartamentoRequestDto dto) {
        return departamentoRepository.findById(id)
                .map(d -> {
                    d.setNombre(dto.nombre());
                    d.setPresupuesto(dto.presupuesto());
                    return departamentoRepository.save(d);
                })
                .orElse(null); //Sustituyendo a la excepcion
    }

    public void deleteDepartamento(Long id) {
        Departamento d = getDepartamentoById(id);
        departamentoRepository.delete(d);
    }
}
