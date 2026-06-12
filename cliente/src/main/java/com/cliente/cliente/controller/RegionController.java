package com.cliente.cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cliente.cliente.DTO.RegionDTO;
import com.cliente.cliente.model.Region;
import com.cliente.cliente.service.RegionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> obtenerTodas() {
        List<RegionDTO> regiones = regionService.obtenerTodas();

        if (regiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(regiones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarRegionPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(regionService.buscarPorId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Región no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarRegion(@Valid @RequestBody Region region) {
        try {
            return new ResponseEntity<>(regionService.guardarRegion(region), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear región", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            return new ResponseEntity<>(regionService.actualizarRegion(id, region), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar región", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(regionService.eliminarRegion(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar región", HttpStatus.BAD_REQUEST);
        }
    }
}
