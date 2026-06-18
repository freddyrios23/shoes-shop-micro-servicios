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

import com.Zapatillas.zapatillas.DTO.MarcaDTO;
import com.Zapatillas.zapatillas.model.Marca;
import com.Zapatillas.zapatillas.service.MarcaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Marcas",description = "Endpoints para gestionar las marcas de zapatillas")
@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @Operation(
        summary = "Listar marcas",
        description = "Obtiene todas las marcas registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<MarcaDTO>> todasLasMarcas(){
        List<MarcaDTO> marca = marcaService.obtenerTodas();
        if (marca.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(marca,HttpStatus.OK);
    } 

    @Operation(
        summary = "Buscar marca por ID",
        description = "Obtiene una marca específica según su id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMarcaPorId(@PathVariable Integer id){
        try {
            MarcaDTO marca = marcaService.buscarPorId(id);
            return new ResponseEntity<>(marca,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro la marca",HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Crear marca",
        description = "Registra una nueva marca de zapatillas"
    )
    @PostMapping
    public ResponseEntity<?> agregarMarca (@Valid @RequestBody Marca marca){
        try {
            return new ResponseEntity<>(marcaService.guardarMarca(marca),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo la marca",HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Actualizar marca",
        description = "Actualiza los datos de una marca existente"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> editarMarca(@PathVariable Integer id,@Valid @RequestBody Marca marca){
        try {
            Marca editada = marcaService.actualizarMarca(id, marca);
            return new ResponseEntity<>(editada,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("marca no encontrada",HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Eliminar marca",
        description = "Elimina una marca según su id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarca (@PathVariable Integer id){
        String resultado = marcaService.eliminarMarca(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(resultado,HttpStatus.NOT_FOUND);
        }
    }
}

