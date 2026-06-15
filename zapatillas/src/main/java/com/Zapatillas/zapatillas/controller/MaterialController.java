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

import com.Zapatillas.zapatillas.DTO.MaterialDTO;
import com.Zapatillas.zapatillas.model.Material;
import com.Zapatillas.zapatillas.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "materiales",description = "Endpoints para gestionar el material de la zapatilla")
@RestController
@RequestMapping("/api/v1/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @Operation(
        summary = "Listar materiales",
        description = "Obtiene todos los materiales registrados en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<MaterialDTO>> todosLosMateriales() {
        List<MaterialDTO> materiales = materialService.obtenerTodos();
        if (materiales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(materiales, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar material",
        description = "busca el mateial de la zapatilla por su id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMaterialPorId(@PathVariable Integer id) {
        try {
            MaterialDTO material = materialService.buscarPorId(id);
            return new ResponseEntity<>(material, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontró el material", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Registra el material",
        description = "Agrega el material de la zapatilla al sistema"
    )
    @PostMapping
    public ResponseEntity<?> agregarMaterial(@Valid @RequestBody Material material) {
        try {
            return new ResponseEntity<>(materialService.guardarMaterial(material), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se guardó el material", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Edita material",
        description = "Actualiza el matrial de la zapatilla por su id"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> editarMaterial(@PathVariable Integer id, @Valid @RequestBody Material material) {
        try {
            Material editado = materialService.actualizarMaterial(id, material);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Material no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Elimina material",
        description = "Elimina el material de la zapatilla por su id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMaterial(@PathVariable Integer id) {
        String resultado = materialService.eliminarMaterial(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
