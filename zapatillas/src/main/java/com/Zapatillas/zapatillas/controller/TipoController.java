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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public ResponseEntity<List<TipoDTO>> todasLosTipos(){
        List<TipoDTO> tipos = tipoService.obtenertodos();
        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tipos,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTipoPorId(@PathVariable Integer id){
        try {
            TipoDTO tipo = tipoService.buscarPorId(id);
            return new ResponseEntity<>(tipo,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro el tipo",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarTipo(@Valid @RequestBody Tipo tipo){
        try {
            return new ResponseEntity<>(tipoService.guardaTipo(tipo),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo el Tipo",HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editarTipo(@PathVariable Integer id,@Valid @RequestBody Tipo tipo){
        try {
            Tipo editada =  tipoService.actualizarTipo(id, tipo);
            return new ResponseEntity<>(editada,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Tipo no encontrado",HttpStatus.NOT_FOUND);
        }
    }

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