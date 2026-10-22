package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.FichajeRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeResponseDto;
import com.salesianos.dam.Registro_jornada.service.FichajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados")
@Validated
@Tag(name = "Fichaje Controller", description = "Un controlador de los fichajes de los empleados")
public class FichajeController {

    private final FichajeService fichajeService;

    @Operation(
            summary = "Fichar",
            description = "Registra una ENTRADA o una SALIDA para un empleado. Si no se indica el tipo en el cuerpo, se alterna automáticamente con el último fichaje"
    )
    @PostMapping("/{empleadoId}/fichar")
    public ResponseEntity<FichajeResponseDto> ficharEmpleado(
            @PathVariable @Min(value = 1) Long empleadoId,
            @RequestBody(required = false) FichajeRequestDto fichaje) {

        return ResponseEntity.status(201)
                .body(FichajeResponseDto.of(
                        fichajeService.ficharEmpleado(empleadoId, fichaje != null ? fichaje.tipo() : null)));
    }

    @Operation(
            summary = "Obtener el historial de fichajes de un empleado",
            description = "Permite obtener todos los fichajes de un empleado ordenados del más antiguo al más reciente"
    )
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
