package com.boletas20.boletas20.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boletas20.boletas20.Model.Boletas;
import com.boletas20.boletas20.Service.BoletasService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "BoletasController",description = "Endpoints para gestionar todas las boletas de la zapatilla")
@RestController
@RequestMapping("/api/v1/boletas_zapatillas")
public class BoletasController {
    @Autowired
    private BoletasService boletasService;


    @GetMapping
    public ResponseEntity<List<Boletas>> todasLasboletasZapatillas(){
        List<Boletas> boletas = boletasService.obtenerTodas();
        if (boletas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boletas,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregaraRelacion(@RequestBody Boletas relacion){
        try {
            return new ResponseEntity<>(boletasService.guardarRelacion(relacion),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo la relacion",HttpStatus.BAD_REQUEST);
        }
    }
}
