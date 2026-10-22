package com.salesianos.dam.Registro_jornada.utils;

import com.salesianos.dam.Registro_jornada.model.Departamento;
import com.salesianos.dam.Registro_jornada.model.Empleado;
import com.salesianos.dam.Registro_jornada.model.Fichaje;
import com.salesianos.dam.Registro_jornada.model.TipoFichaje;
import com.salesianos.dam.Registro_jornada.repository.DepartamentoRepository;
import com.salesianos.dam.Registro_jornada.repository.EmpleadoRepository;
import com.salesianos.dam.Registro_jornada.repository.FichajeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final FichajeRepository fichajeRepository;

    @PostConstruct
    public void initData() {

        Departamento d1 = Departamento.builder()
                .nombre("Desarrollo")
                .presupuesto(new BigDecimal("70000.00"))
                .build();

        departamentoRepository.save(d1);


        Empleado e1 = Empleado.builder()
                .nombreCompleto("Ana Pérez García")
                .cargo("Desarrolladora")
                .salario(new BigDecimal("32000.00"))
                .departamento(d1)
                .build();

        empleadoRepository.save(e1);


        LocalDate ayer = LocalDate.now().minusDays(1);

        Fichaje f1 = Fichaje.builder()
                .momento(ayer.atTime(8, 0))
                .tipo(TipoFichaje.ENTRADA)
                .empleado(e1)
                .build();

        fichajeRepository.save(f1);

    }
}
