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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boletas20.boletas20.DTO.MetodoEnvioDTO;
import com.boletas20.boletas20.Model.MetodoEnvio;
import com.boletas20.boletas20.Service.MetodoEnvioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/metodos_envio")
public class MetodoEnvioController {
    
    @Autowired
    private MetodoEnvioService metodoEnvioService;

    @GetMapping
    public ResponseEntity<List<MetodoEnvioDTO>> todosLosEnvios(){
        List<MetodoEnvioDTO> envios = metodoEnvioService.obtenerTodas();

        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(envios,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEnvioPorId(@PathVariable Integer id){
        try {
            MetodoEnvioDTO envio = metodoEnvioService.buscarPorId(id);
            return new ResponseEntity<>(envio,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no se encontro el envio",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarMetodoPago(@Valid @RequestBody MetodoEnvio metodoEnvio){
        try {
            return new ResponseEntity<>(metodoEnvioService.guardarMetodoEnvio(metodoEnvio),HttpStatus.CREATED);
        } catch (Exception e) {
        return new ResponseEntity<>("no se guardo la envio",HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editarEnvio(@PathVariable Integer id ,@Valid @RequestBody MetodoEnvio metodoEnvio){
        try {
            MetodoEnvio editada = metodoEnvioService.actualizarMetodoEnvio(id, metodoEnvio);
            return new ResponseEntity<>(editada,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("envio no encontrada",HttpStatus.NOT_FOUND);
        }
    }   
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMetodoEnvio(@PathVariable Integer id ){
        String resultado = metodoEnvioService.eliminarMetodoEnvio(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(resultado,HttpStatus.NOT_FOUND);
        }
    }
    

}
