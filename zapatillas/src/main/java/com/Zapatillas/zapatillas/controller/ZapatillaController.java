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

import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.service.ZapatillaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Zapatillas", description = "Endpoints para gestion zapatillas")
@RestController
@RequestMapping("/api/v1/zapatillas")
public class ZapatillaController {
    
    @Autowired
    private ZapatillaService zapatillaService;

    @Operation(
        summary = "Listar Zapatillas",
        description = "Obtiene todas las zapatillas que stan registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<ZapatillaDTO>> todosLasZapatillas(){
        List<ZapatillaDTO> zapatillas = zapatillaService.obtenerTodas();
        if (zapatillas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(zapatillas,HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar Zapatilla",
        description = "Obtiene La zapatilla por un id especifico"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarZapatillaPorId(@PathVariable Integer id){
        try {
            ZapatillaDTO zapatilla =  zapatillaService.buscarPorId(id);
            return new ResponseEntity<>(zapatilla,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro la Zapatilla",HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Agregar Zapatilla",
        description = "Registra una nueva zapatilla en el sistema"
    )
    @PostMapping
    public ResponseEntity<?> agregarZapatilla(@Valid @RequestBody Zapatilla zapatilla){
        try {
            return new ResponseEntity<>(zapatillaService.guardarZapatilla(zapatilla),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo la zapatilla",HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Editar Zapatilla",
        description = "Edita una zapatilla por un id especifico"
    )    
    @PatchMapping("/{id}")
    public ResponseEntity<?> editarZapatilla(@PathVariable Integer id, @Valid @RequestBody Zapatilla zapatilla){
        try {
            Zapatilla editada = zapatillaService.actualizarZapatilla(id, zapatilla);
            return new ResponseEntity<>(editada, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("zapatilla no encontrada", HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        summary = "Elmina Zapatilla",
        description = "elimina una zapatilla por un id especifico"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarZapatilla(@PathVariable Integer id){

        String resultado = zapatillaService.eliminarZapatilla(id);

        if(resultado.contains("exitosamente")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }


}
