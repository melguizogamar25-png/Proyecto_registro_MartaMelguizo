package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.EmpleadoResponseDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeResponseDto;
import com.salesianos.dam.Registro_jornada.service.EmpleadoService;
import com.salesianos.dam.Registro_jornada.service.FichajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Empleado Controller", description = "Un controlador de empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Operation(
            summary = "Obtener todos los empleados",
            description = "Permite obtener todos los empleados de la empresa"
    )
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDto>> getAllEmpleado() {
        return ResponseEntity.ok(
                empleadoService.getAllEmpleados()
                        .stream()
                        .map(EmpleadoResponseDto::of)
                        .toList());
    }

    @Operation(
            summary = "Obtener un empleado por su id",
            description = "Permite obtener el detalle de un empleado, incluyendo el nombre de su departamento"
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> getEmpleadoById(
            @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.getEmpleadosById(id)));
    }
    @Operation(
            summary = "Crear un empleado",
            description = "Permite crear un empleado, opcionalmente asignado a un departamento. La suma de los salarios del departamento no puede superar su presupuesto"
    )
    @PostMapping
    public ResponseEntity<EmpleadoResponseDto> addEmpleado(
            @Valid @RequestBody EmpleadoRequestDto empleado) {
        return ResponseEntity.status(201)
                .body(EmpleadoResponseDto.of(empleadoService.addEmpleado(empleado)));
    }

    @Operation(
            summary = "Modificar un empleado",
            description = "Permite modificar el nombre, el cargo y el salario de un empleado. Para cambiar de departamento se usa el endpoint de asignación"
    )
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> updateEmpleado(
            @PathVariable @Min(value = 1) Long id,
            @Valid @RequestBody EmpleadoRequestDto empleado) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.updateEmpleado(id, empleado)));
    }

    @Operation(
            summary = "Borrar un empleado",
            description = "Permite borrar un empleado junto con su historial de fichajes"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(
            @PathVariable @Min(value = 1) Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Asignar un empleado a un departamento",
            description = "Permite asignar un empleado a un departamento existente"
    )
    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public ResponseEntity<EmpleadoResponseDto> asignarDepartamento(
            @PathVariable @Min(value = 1) Long empleadoId,
            @PathVariable @Min(value = 1) Long deptoId) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.asignarDepartamento(empleadoId, deptoId)));
    }

}
