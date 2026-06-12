package com.Zapatillas.zapatillas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zapatillas.zapatillas.model.Colores;
import com.Zapatillas.zapatillas.service.ColoresService;

@RestController
@RequestMapping("/api/v1/colores")
public class ColoresController {
    
    @Autowired
    private ColoresService coloresService;

    @GetMapping
    public ResponseEntity<List<Colores>> todosLosColores() {
        List<Colores> colores = coloresService.obtenerTodos();

        if (colores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colores, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> agregarColores(@RequestBody Colores colores) {
        return new ResponseEntity<>(
                coloresService.guardarRelacion(colores),
                HttpStatus.CREATED
        );

    }
}
