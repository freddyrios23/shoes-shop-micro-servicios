package com.boletas20.boletas20.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boletas20.boletas20.DTO.BoletaDTO;
import com.boletas20.boletas20.Model.Boleta;
import com.boletas20.boletas20.Service.BoletaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@Tag(name = "boletas",description = "Endpoints para gestionar las boletas de las zapatillas")
@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping
    public ResponseEntity<List<BoletaDTO>> todasLasBoletas(){
        List<BoletaDTO> boleta = boletaService.obtenerTodas();
        if (boleta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boleta,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarBoletaPorId(@PathVariable Integer id){
        try {
            BoletaDTO boleta = boletaService.buscarPorId(id);
            return new ResponseEntity<>(boleta,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro la boleta",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarBoleta(@Valid @RequestBody Boleta boleta){
        try {
            return new ResponseEntity<>(boletaService.guardarBoleta(boleta),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("no se guardo la boleta",HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editarBoleta(@PathVariable Integer id,@RequestBody Boleta boleta){
        try {
            Boleta editada = boletaService.actualizarBoleta(id, boleta);
            return new ResponseEntity<>(editada,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Boleta no encontrada",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBoleta(@PathVariable Integer id,@Valid @RequestBody Boleta boleta){
        try {
            Boleta newBoleta = boletaService.actualizarBoleta(id, boleta);
            return new ResponseEntity<>(newBoleta,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Boleta no encontrada",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarBoleta(@PathVariable Integer id){
        String resultado = boletaService.eliminarBoleta(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(resultado,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-zapatilla/{zapatillaId}")
    public ResponseEntity<?> buscarPorZapatilla(@PathVariable Integer zapatillaId) {
        try {
            BoletaDTO boleta = boletaService.buscarPorZapatilla(zapatillaId);
            return new ResponseEntity<>(boleta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro boleta para esa zapatilla", HttpStatus.NOT_FOUND);
        }
    }
}
