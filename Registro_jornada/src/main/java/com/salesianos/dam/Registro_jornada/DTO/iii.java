/*package com.salesianos.dam.registrojornada.service;

import com.salesianos.dam.registrojornada.dto.DepartamentoRequestDTO;
import com.salesianos.dam.registrojornada.error.notfound.DepartamentoNoEncontradaException;
import com.salesianos.dam.registrojornada.model.Departamento;
import com.salesianos.dam.registrojornada.repo.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//departamentos #3 - CRUD de departamentos
@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNoEncontradaException(id));
    }

    public Departamento addDepartamento(DepartamentoRequestDTO dto) {
        return departamentoRepository.save(dto.to());
    }

    public Departamento updateDepartamento(Long id, DepartamentoRequestDTO dto) {
        return departamentoRepository.findById(id)
                .map(d -> {
                    d.setNombre(dto.nombre());
                    d.setPresupuesto(dto.presupuesto());
                    return departamentoRepository.save(d);
                })
                .orElseThrow(() -> new DepartamentoNoEncontradaException(id));
    }

    public void deleteDepartamento(Long id) {
        Departamento d = getDepartamentoById(id);
        departamentoRepository.delete(d);
    }

}*/
