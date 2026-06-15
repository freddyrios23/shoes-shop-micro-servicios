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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Sexos",description = "Endpoints para gestionar el sexo de la zapatilla")
@RestController
@RequestMapping("/api/v1/sexos")
public class SexoController {
    
    @Autowired
    private SexoService sexoService;

    @Operation(
        summary = "Listar sexos",
        description = "Obtiene todos los sexos de zapatillas que estan registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<SexoDTO>> obtenerTodosSexos() {
        List<SexoDTO> sexos = sexoService.obtenerTodos();
        if (sexos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sexos, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar sexo",
        description = "busca el sexo de la zapatilla por su id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarSexoPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(sexoService.buscarPorId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("sexo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Registra sexo",
        description = "Registra el sexo de la zapatilla en el sistema"
    )
    @PostMapping
    public ResponseEntity<?> agregarSexo(@Valid @RequestBody Sexo sexo) {
        try {
            return new ResponseEntity<>(sexoService.guardarSexo(sexo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear sexo", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Actualizar sexp",
        description = "Edita el sexo de la zapatilla por su id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSexon(@PathVariable Integer id, @RequestBody Sexo sexo) {
        try {
            return new ResponseEntity<>(sexoService.actualizarSexo(id, sexo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar sexo", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Eliminar sexo",
        description = "Elimina del sistema el sexo de la zapatilla"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSexo(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(sexoService.eliminarSexo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar Sexo", HttpStatus.BAD_REQUEST);
        }
    }
}
