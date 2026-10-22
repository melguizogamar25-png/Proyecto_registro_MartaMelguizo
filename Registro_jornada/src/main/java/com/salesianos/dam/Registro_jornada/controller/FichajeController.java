package com.salesianos.dam.Registro_jornada.controller;

import com.salesianos.dam.Registro_jornada.service.FichajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fichaje")
@Validated

public class FichajeController {

    private final FichajeService fichajeService;



}
