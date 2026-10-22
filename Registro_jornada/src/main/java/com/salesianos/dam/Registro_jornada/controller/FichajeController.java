package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.FichajeRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeResponseDto;
import com.salesianos.dam.Registro_jornada.service.FichajeService;
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

public class FichajeController {

    private final FichajeService fichajeService;

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
