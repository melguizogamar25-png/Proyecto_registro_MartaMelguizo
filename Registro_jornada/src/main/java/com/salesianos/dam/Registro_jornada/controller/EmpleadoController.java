package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.EmpleadoResponseDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeResponseDto;
import com.salesianos.dam.Registro_jornada.service.EmpleadoService;
import com.salesianos.dam.Registro_jornada.service.FichajeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados")
@Validated
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final FichajeService fichajeService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDto>> getAllEmpleado() {
        return ResponseEntity.ok(
                empleadoService.getAllEmpleados()
                        .stream()
                        .map(EmpleadoResponseDto::of)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> getEmpleadoById(
            @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.getEmpleadosById(id)));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDto> addEmpleado(
            @Valid @RequestBody EmpleadoRequestDto empleado) {
        return ResponseEntity.status(201)
                .body(EmpleadoResponseDto.of(empleadoService.addEmpleado(empleado)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> updateEmpleado(
            @PathVariable @Min(value = 1) Long id,
            @Valid @RequestBody EmpleadoRequestDto empleado) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.updateEmpleado(id, empleado)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(
            @PathVariable @Min(value = 1) Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public ResponseEntity<EmpleadoResponseDto> asignarDepartamento(
            @PathVariable @Min(value = 1) Long empleadoId,
            @PathVariable @Min(value = 1) Long deptoId) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.asignarDepartamento(empleadoId, deptoId)));
    }

    @PostMapping("/{empleadoId}/fichar")
    public ResponseEntity<FichajeResponseDto> ficharEmpleado(
            @PathVariable @Min(value = 1) Long empleadoId,
            @RequestBody(required = false) FichajeRequestDto fichaje) {

        return ResponseEntity.status(201)
                .body(FichajeResponseDto.of(
                        fichajeService.ficharEmpleado(empleadoId, fichaje != null ? fichaje.tipo() : null)));
    }

    @GetMapping("/{empleadoId}/fichajes")
    public ResponseEntity<List<FichajeResponseDto>> getFichajesByEmpleado(
            @PathVariable @Min(value = 1) Long empleadoId) {
        return ResponseEntity.ok(
                fichajeService.getFichajesByEmpleado(empleadoId)
                        .stream()
                        .map(FichajeResponseDto::of)
                        .toList());
    }
}
