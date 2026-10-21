/*



    @PostMapping("/{empleadoId}/fichar") //fichar #6
    public ResponseEntity<FichajeResponseDTO> ficharEmpleado( //fichar #6
            @PathVariable @Min(value = 1) Long empleadoId, //fichar #6
            @RequestBody(required = false) FichajeRequestDTO fichaje) { //fichar #6

        return ResponseEntity.status(201) //fichar #6
                .body(FichajeResponseDTO.of( //fichar #6
                        fichajeService.ficharEmpleado(empleadoId, fichaje != null ? fichaje.tipo() : null))); //fichar #6
    } //fichar #6

    @Operation( //swagger #10
            summary = "Obtener el historial de fichajes de un empleado", //swagger #10
            description = "Permite obtener todos los fichajes de un empleado ordenados del más antiguo al más reciente" //swagger #10
    ) //swagger #10
    @ApiResponses( //swagger #10
            { //swagger #10
                @ApiResponse( //swagger #10
                        responseCode = "200", //swagger #10
                        description = "Historial de fichajes", //swagger #10
                        content = @Content( //swagger #10
                                mediaType = "application/json", //swagger #10
                                array = @ArraySchema(schema = @Schema(implementation = FichajeResponseDTO.class)), //swagger #10
                                examples = @ExampleObject( //swagger #10
                                        name = "Historial de fichajes", //swagger #10
                                        summary = "Ejemplo de historial con entrada y salida", //swagger #10
                                        value = """
                                        [
                                          {
                                            "id": 1,
                                            "momento": "2026-10-19T08:00:00",
                                            "tipo": "ENTRADA",
                                            "empleadoId": 1
                                          },
                                          {
                                            "id": 2,
                                            "momento": "2026-10-19T15:00:00",
                                            "tipo": "SALIDA",
                                            "empleadoId": 1
                                          }
                                        ]
                                                """ //swagger #10
                                ) //swagger #10
                        ) //swagger #10
                ), //swagger #10
                @ApiResponse( //swagger #10
                        responseCode = "404", //swagger #10
                        description = "No existe un empleado con ese id", //swagger #10
                        content = @Content( //swagger #10
                                mediaType = "application/problem+json", //swagger #10
                                schema = @Schema(implementation = ProblemDetail.class), //swagger #10
                                examples = @ExampleObject( //swagger #10
                                        name = "Error 404", //swagger #10
                                        summary = "No existe un empleado con ese id", //swagger #10
                                        value = """
                                                {
                                                  "detail": "Empleado no encontrado con id 99",
                                                  "instance": "/api/v1/empleados/99/fichajes",
                                                  "status": 404,
                                                  "title": "Recurso no encontrado",
                                                  "type": "https://example.com/errors/not-found"
                                                }
                                                """ //swagger #10
                                ) //swagger #10
                        ) //swagger #10
                ) //swagger #10
            } //swagger #10
    ) //swagger #10
    @GetMapping("/{empleadoId}/fichajes") //historial #7
    public ResponseEntity<List<FichajeResponseDTO>> getFichajesByEmpleado( //historial #7
            @PathVariable @Min(value = 1) Long empleadoId) { //historial #7

        return ResponseEntity.ok( //historial #7
                fichajeService.getFichajesByEmpleado(empleadoId) //historial #7
                        .stream() //historial #7
                        .map(FichajeResponseDTO::of) //historial #7
                        .toList()); //historial #7
    } //historial #7

} //empleados #4
 */


