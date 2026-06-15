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

import com.Zapatillas.zapatillas.DTO.TipoDTO;
import com.Zapatillas.zapatillas.model.Tipo;
import com.Zapatillas.zapatillas.service.TipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tipos",description = "Endpoints para gestionas los tipos de zapatillas")
@RestController
@RequestMapping("/api/v1/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @Operation(
        summary = "Listar tipos",
        description = "Obtiene todos los tipos de zapatillas que stan registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<TipoDTO>> todasLosTipos(){
        List<TipoDTO> tipos = tipoService.obtenertodos();
        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tipos,HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar tipo de zapatilla",
        description = "Obtiene el tipo de zapatilla segun su id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTipoPorId(@PathVariable Integer id){
        try {
            TipoDTO tipo = tipoService.buscarPorId(id);
            return new ResponseEntity<>(tipo,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro el tipo",HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Resgistra el tipo",
        description = "agrega el tipo de zapatilla al sistema"
    )
    @PostMapping
    public ResponseEntity<?> agregarTipo(@Valid @RequestBody Tipo tipo){
        try {
            return new ResponseEntity<>(tipoService.guardaTipo(tipo),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo el Tipo",HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Edita el tipo",
        description = "Edita el tipo de zapatilla por su id"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> editarTipo(@PathVariable Integer id,@Valid @RequestBody Tipo tipo){
        try {
            Tipo editada =  tipoService.actualizarTipo(id, tipo);
            return new ResponseEntity<>(editada,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Tipo no encontrado",HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Elimina el tipo",
        description = "Elimina del sistema el tipo de zapatilla"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipo(@PathVariable Integer id){
        String resultado = tipoService.eliminarTipo(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(resultado,HttpStatus.NOT_FOUND);
        }
    }
}