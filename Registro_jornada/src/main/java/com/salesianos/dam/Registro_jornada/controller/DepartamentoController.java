package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.DepartamentoRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.DepartamentoResponseDto;
import com.salesianos.dam.Registro_jornada.service.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departamentos")
@Validated
@Tag(name = "Departamento Controller", description = "Un controlador de departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @Operation(
            summary = "Obtener todos los departamentos",
            description = "Permite obtener todos los departamentos de la empresa"
    )
    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDto>> getAllDepartamentos() {
        return ResponseEntity.ok(
                departamentoService.getAllDepartamentos()
                        .stream()
                        .map(DepartamentoResponseDto::of)
                        .toList());
    }

    @Operation(
            summary = "Obtener un departamento por su id",
            description = "Permite obtener el detalle de un departamento"
    )
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> getDepartamentoById(
            @PathVariable @Min(value = 1) Long id) {
       return ResponseEntity.ok(
               DepartamentoResponseDto.of(departamentoService.getDepartamentoById(id)));
    }

    @Operation(
            summary = "Crear un departamento",
            description = "Permite crear un nuevo departamento. El nombre debe ser único"
    )
    @PostMapping
    public ResponseEntity<DepartamentoResponseDto> addDepartamento(
            @Valid @RequestBody DepartamentoRequestDto departamento) {
        return ResponseEntity.status(201)
                .body(DepartamentoResponseDto.of(departamentoService.addDepartamento(departamento)));
    }

    @Operation(
            summary = "Modificar un departamento",
            description = "Permite modificar el nombre y el presupuesto de un departamento"
    )
   @PutMapping("/{id}")
   public ResponseEntity<DepartamentoResponseDto> updateDepartamento(
           @PathVariable @Min(value = 1) Long id,
           @Valid @RequestBody DepartamentoRequestDto departamento) {
        return ResponseEntity.ok(
                DepartamentoResponseDto.of(departamentoService.updateDepartamento(id, departamento)));
   }

    @Operation(
            summary = "Borrar un departamento",
            description = "Permite borrar un departamento que no tenga empleados asociados"
    )
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(
            @PathVariable @Min(value = 1) Long id) {
       departamentoService.deleteDepartamento(id);
       return ResponseEntity.noContent().build();
   }
}
