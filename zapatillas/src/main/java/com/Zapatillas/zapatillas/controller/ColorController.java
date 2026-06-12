package com.Zapatillas.zapatillas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zapatillas.zapatillas.DTO.ColorDTO;
import com.Zapatillas.zapatillas.model.Color;
import com.Zapatillas.zapatillas.service.ColorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/color")
public class ColorController {
@Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorDTO>> todosLosColores(){
        List<ColorDTO> colores = colorService.obtenerTodos();
        if (colores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colores,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarColorPorId(@PathVariable Integer id){
        try {
            ColorDTO colores =  colorService.buscarPorId(id);
            return new ResponseEntity<>(colores,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro el color",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarColor(@Valid @RequestBody Color color){
        try {
            return new ResponseEntity<>(colorService.guardarColor(color),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo la zapatilla",HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editarcolor(@PathVariable Integer id, @Valid @RequestBody Color color){
        try {
            Color editada = colorService.actualizarColor(id, color);
            return new ResponseEntity<>(editada, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("color no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarColores(@PathVariable Integer id){

        String resultado = colorService.eliminarColor(id);

        if(resultado.contains("exitosamente")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
