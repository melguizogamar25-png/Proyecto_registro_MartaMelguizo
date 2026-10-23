package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.DepartamentoRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.DepartamentoResponseDto;
import com.salesianos.dam.Registro_jornada.service.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de departamentos",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DepartamentoResponseDto.class)),
                                    examples = @ExampleObject(
                                            name = "Listado de departamentos",
                                            summary = "Ejemplo con el departamento de ejemplo y su empleado",
                                            value = """
                                                    [
                                                      {
                                                        "id": 1,
                                                        "nombre": "Desarrollo",
                                                        "presupuesto": 70000.00,
                                                        "empleados": [
                                                          {
                                                            "id": 1,
                                                            "nombreCompleto": "Ana Pérez García",
                                                            "cargo": "Desarrolladora",
                                                            "salario": 32000.00,
                                                            "departamentoNombre": "Desarrollo"
                                                          }
                                                        ]
                                                      }
                                                    ]
                                                    """
                                    )
                            )
                    )
            }
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalle del departamento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DepartamentoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Departamento",
                                            summary = "Ejemplo de departamento",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombre": "Desarrollo",
                                                      "presupuesto": 70000.00,
                                                      "empleados": [
                                                        {
                                                          "id": 1,
                                                          "nombreCompleto": "Ana Pérez García",
                                                          "cargo": "Desarrolladora",
                                                          "salario": 32000.00,
                                                          "departamentoNombre": "Desarrollo"
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un departamento con ese id",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe un departamento con ese id",
                                            value = """
                                                    {
                                                      "detail": "Departamento no encontrado con id 99",
                                                      "instance": "/api/v1/departamentos/99",
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Departamento creado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DepartamentoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Departamento creado",
                                            summary = "Ejemplo de departamento creado",
                                            value = """
                                                    {
                                                      "id": 2,
                                                      "nombre": "Marketing",
                                                      "presupuesto": 40000.00,
                                                      "empleados": []
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Los datos enviados no son válidos",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 400",
                                            summary = "Error de validación de un campo",
                                            value = """
                                                    {
                                                      "detail": "Error de validación",
                                                      "instance": "/api/v1/departamentos",
                                                      "invalid-params": [
                                                        {
                                                          "object": "departamentoRequestDTO",
                                                          "message": "El nombre del departamento es obligatorio",
                                                          "field": "nombre"
                                                        }
                                                      ],
                                                      "status": 400,
                                                      "title": "Bad Request",
                                                      "type": "about:blank"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Ya existe un departamento con ese nombre",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 409",
                                            summary = "Ya existe un departamento con ese nombre",
                                            value = """
                                                    {
                                                      "detail": "La operación no se puede realizar: el nombre ya existe o el recurso tiene elementos asociados",
                                                      "instance": "/api/v1/departamentos",
                                                      "status": 409,
                                                      "title": "Conflicto de integridad de datos",
                                                      "type": "https://example.com/errors/data-integrity"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Departamento modificado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DepartamentoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Departamento modificado",
                                            summary = "Ejemplo de departamento modificado",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombre": "Desarrollo",
                                                      "presupuesto": 70000.00,
                                                      "empleados": [
                                                        {
                                                          "id": 1,
                                                          "nombreCompleto": "Ana Pérez García",
                                                          "cargo": "Desarrolladora",
                                                          "salario": 32000.00,
                                                          "departamentoNombre": "Desarrollo"
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Los datos enviados no son válidos",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 400",
                                            summary = "Error de validación de un campo",
                                            value = """
                                                    {
                                                      "detail": "Error de validación",
                                                      "instance": "/api/v1/departamentos/1",
                                                      "invalid-params": [
                                                        {
                                                          "object": "departamentoRequestDTO",
                                                          "message": "El presupuesto del departamento debe ser mayor o igual que 0",
                                                          "field": "presupuesto"
                                                        }
                                                      ],
                                                      "status": 400,
                                                      "title": "Bad Request",
                                                      "type": "about:blank"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un departamento con ese id",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe un departamento con ese id",
                                            value = """
                                                    {
                                                      "detail": "Departamento no encontrado con id 99",
                                                      "instance": "/api/v1/departamentos/99",
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
                            description = "Ya existe un departamento con ese nombre",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 409",
                                            summary = "Ya existe un departamento con ese nombre",
                                            value = """
                                                    {
                                                      "detail": "La operación no se puede realizar: el nombre ya existe o el recurso tiene elementos asociados",
                                                      "instance": "/api/v1/departamentos/1",
                                                      "status": 409,
                                                      "title": "Conflicto de integridad de datos",
                                                      "type": "https://example.com/errors/data-integrity"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
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
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "204", description = "Departamento borrado"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un departamento con ese id",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe un departamento con ese id",
                                            value = """
                                                    {
                                                      "detail": "Departamento no encontrado con id 99",
                                                      "instance": "/api/v1/departamentos/99",
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
                            description = "El departamento tiene empleados asociados",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 409",
                                            summary = "El departamento tiene empleados asociados",
                                            value = """
                                                    {
                                                      "detail": "La operación no se puede realizar: el nombre ya existe o el recurso tiene elementos asociados",
                                                      "instance": "/api/v1/departamentos/1",
                                                      "status": 409,
                                                      "title": "Conflicto de integridad de datos",
                                                      "type": "https://example.com/errors/data-integrity"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(
            @PathVariable @Min(value = 1) Long id) {
        departamentoService.deleteDepartamento(id);
        return ResponseEntity.noContent().build();
    }
}
