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

import com.Zapatillas.zapatillas.model.Tipos;
import com.Zapatillas.zapatillas.service.TiposService;


@RestController
@RequestMapping("/api/v1/tipos_zapatillas")
public class TiposController {
    @Autowired
    private TiposService tiposService;

    @GetMapping
    public ResponseEntity<List<Tipos>> todasLosTiposZapatillas(){
        List<Tipos> tipos = tiposService.obtenerTodas();
        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tipos,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregaraRelacion(@RequestBody Tipos relacion){
        try {
            return new ResponseEntity<>(tiposService.guardarRelacion(relacion),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo la relacion",HttpStatus.BAD_REQUEST);
        }
    }
}
