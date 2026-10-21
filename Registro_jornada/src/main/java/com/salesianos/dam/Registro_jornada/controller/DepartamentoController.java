package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.DepartamentoRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.DepartamentoResponseDto;
import com.salesianos.dam.Registro_jornada.service.DepartamentoService;
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
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDto>> getAllDepartamentos() {
        return ResponseEntity.ok(
                departamentoService.getAllDepartamentos()
                        .stream()
                        .map(DepartamentoResponseDto::of)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> getDepartamentoById(
            @PathVariable @Min(value = 1) Long id) {
       return ResponseEntity.ok(
               DepartamentoResponseDto.of(departamentoService.getDepartamentoById(id)));
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponseDto> addDepartamento(
            @Valid @RequestBody DepartamentoRequestDto departamento) {
        return ResponseEntity.status(201)
                .body(DepartamentoResponseDto.of(departamentoService.addDepartamento(departamento)));
    }

   @PutMapping("/{id}")
   public ResponseEntity<DepartamentoResponseDto> updateDepartamento(
           @PathVariable @Min(value = 1) Long id,
           @Valid @RequestBody DepartamentoRequestDto departamento) {
        return ResponseEntity.ok(
                DepartamentoResponseDto.of(departamentoService.updateDepartamento(id, departamento)));
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(
            @PathVariable @Min(value = 1) Long id) {
       departamentoService.deleteDepartamento(id);
       return ResponseEntity.noContent().build();
   }
}
