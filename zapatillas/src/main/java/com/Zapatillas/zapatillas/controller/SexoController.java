package com.Zapatillas.zapatillas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zapatillas.zapatillas.DTO.SexoDTO;
import com.Zapatillas.zapatillas.model.Sexo;
import com.Zapatillas.zapatillas.service.SexoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sexos")
public class SexoController {
    
    @Autowired
    private SexoService sexoService;

    @GetMapping
    public ResponseEntity<List<SexoDTO>> obtenerTodosSexos() {
        List<SexoDTO> sexos = sexoService.obtenerTodos();
        if (sexos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sexos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarSexoPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(sexoService.buscarPorId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("sexo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarSexo(@Valid @RequestBody Sexo sexo) {
        try {
            return new ResponseEntity<>(sexoService.guardarSexo(sexo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear sexo", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSexon(@PathVariable Integer id, @RequestBody Sexo sexo) {
        try {
            return new ResponseEntity<>(sexoService.actualizarSexo(id, sexo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar sexo", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSexo(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(sexoService.eliminarSexo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar Sexo", HttpStatus.BAD_REQUEST);
        }
    }
}
