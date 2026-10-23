package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.FichajeRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeResponseDto;
import com.salesianos.dam.Registro_jornada.service.FichajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Fichaje registrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FichajeResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Fichaje registrado",
                                            summary = "Ejemplo de fichaje de salida",
                                            value = """
                                                    {
                                                      "id": 2,
                                                      "momento": "2026-10-23T15:00:00",
                                                      "tipo": "SALIDA",
                                                      "empleadoId": 1
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un empleado con ese id",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe un empleado con ese id",
                                            value = """
                                                    {
                                                      "detail": "Empleado no encontrado con id 99",
                                                      "instance": "/api/v1/empleados/99/fichar",
                                                      "status": 404,
                                                      "title": "Recurso no encontrado",
                                                      "type": "https://example.com/errors/not-found"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "El último fichaje del empleado ya era de ese tipo",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 409",
                                            summary = "El último fichaje del empleado ya era de ese tipo",
                                            value = """
                                                    {
                                                      "detail": "El empleado con id 1 ya tiene como último fichaje una ENTRADA",
                                                      "instance": "/api/v1/empleados/1/fichar",
                                                      "status": 409,
                                                      "title": "Fichaje duplicado",
                                                      "type": "https://example.com/errors/fichaje-duplicado"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Historial de fichajes",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FichajeResponseDto.class)),
                                    examples = @ExampleObject(
                                            name = "Historial de fichajes",
                                            summary = "Ejemplo de historial con la entrada de ejemplo",
                                            value = """
                                                     [
                                                       {
                                                         "id": 1,
                                                         "momento": "2026-10-22T08:00:00",
                                                         "tipo": "ENTRADA",
                                                         "empleadoId": 1
                                                       }
                                                     ]
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un empleado con ese id",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe un empleado con ese id",
                                            value = """
                                                    {
                                                      "detail": "Empleado no encontrado con id 99",
                                                      "instance": "/api/v1/empleados/99/fichajes",
                                                      "status": 404,
                                                      "title": "Recurso no encontrado",
                                                      "type": "https://example.com/errors/not-found"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
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
