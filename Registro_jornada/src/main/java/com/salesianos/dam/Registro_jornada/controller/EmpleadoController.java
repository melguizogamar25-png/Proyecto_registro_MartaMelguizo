package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.DTO.EmpleadoRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.EmpleadoResponseDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeRequestDto;
import com.salesianos.dam.Registro_jornada.DTO.FichajeResponseDto;
import com.salesianos.dam.Registro_jornada.service.EmpleadoService;
import com.salesianos.dam.Registro_jornada.service.FichajeService;
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
import org.apache.coyote.Response;
import org.springframework.http.ProblemDetail;
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de empleados",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EmpleadoResponseDto.class)),
                                    examples = @ExampleObject(
                                            name = "Listado de empleados",
                                            summary = "Ejemplo con el empleado de ejemplo",
                                            value = """
                                                    [
                                                      {
                                                        "id": 1,
                                                        "nombreCompleto": "Ana Pérez García",
                                                        "cargo": "Desarrolladora",
                                                        "salario": 32000.00,
                                                        "departamentoNombre": "Desarrollo"
                                                      }
                                                    ]
                                                    """
                                    )
                            )
                    )
            }
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalle del empleado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmpleadoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Empleado",
                                            summary = "Ejemplo de empleado",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombreCompleto": "Ana Pérez García",
                                                      "cargo": "Desarrolladora",
                                                      "salario": 32000.00,
                                                      "departamentoNombre": "Desarrollo"
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
                                                      "instance": "/api/v1/empleados/99",
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
    public ResponseEntity<EmpleadoResponseDto> getEmpleadoById(
            @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.getEmpleadosById(id)));
    }

    @Operation(
            summary = "Crear un empleado",
            description = "Permite crear un empleado, opcionalmente asignado a un departamento. La suma de los salarios del departamento no puede superar su presupuesto"
    )
    @ApiResponses(
            {
                    //CAMBIO swagger - ejemplo actualizado al DataSeed actual (1 departamento, 1 empleado, 1 fichaje)
                    @ApiResponse(
                            responseCode = "201",
                            description = "Empleado creado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmpleadoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Empleado creado",
                                            summary = "Ejemplo de empleado creado",
                                            value = """
                                                    {
                                                      "id": 2,
                                                      "nombreCompleto": "Pedro Sánchez Mora",
                                                      "cargo": "Tester",
                                                      "salario": 9000.00,
                                                      "departamentoNombre": "Desarrollo"
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
                                                      "instance": "/api/v1/empleados",
                                                      "invalid-params": [
                                                        {
                                                          "object": "empleadoRequestDTO",
                                                          "message": "La suma de los salarios del departamento supera su presupuesto",
                                                          "field": "salario"
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
                            description = "No existe el departamento indicado",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe el departamento indicado",
                                            value = """
                                                    {
                                                      "detail": "Departamento no encontrado con id 99",
                                                      "instance": "/api/v1/empleados",
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Empleado modificado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmpleadoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Empleado modificado",
                                            summary = "Ejemplo de empleado modificado",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombreCompleto": "Ana Pérez García",
                                                      "cargo": "Desarrolladora",
                                                      "salario": 32000.00,
                                                      "departamentoNombre": "Desarrollo"
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
                                                      "instance": "/api/v1/empleados/1",
                                                      "invalid-params": [
                                                        {
                                                          "object": "empleadoRequestDTO",
                                                          "message": "El salario del empleado debe ser mayor que 0",
                                                          "field": "salario"
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
                                                      "instance": "/api/v1/empleados/99",
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
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "204", description = "Empleado borrado"),
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
                                                      "instance": "/api/v1/empleados/99",
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
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Empleado asignado al departamento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmpleadoResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Empleado asignado",
                                            summary = "Ejemplo de empleado con su departamento",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombreCompleto": "Ana Pérez García",
                                                      "cargo": "Desarrolladora",
                                                      "salario": 32000.00,
                                                      "departamentoNombre": "Desarrollo"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe el empleado o el departamento",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "No existe el empleado o el departamento",
                                            value = """
                                                    {
                                                      "detail": "Departamento no encontrado con id 99",
                                                      "instance": "/api/v1/empleados/1/departamento/99",
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
    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public ResponseEntity<EmpleadoResponseDto> asignarDepartamento(
            @PathVariable @Min(value = 1) Long empleadoId,
            @PathVariable @Min(value = 1) Long deptoId) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.of(empleadoService.asignarDepartamento(empleadoId, deptoId)));
    }

}
