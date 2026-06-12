package com.boletas20.boletas20.Controller;

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

import com.boletas20.boletas20.DTO.MetodoPagoDTO;
import com.boletas20.boletas20.Model.MetodoPago;
import com.boletas20.boletas20.Service.MetodoPagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/metodo_pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> obtenerTodosLosPagos(){
        List<MetodoPagoDTO> metodosDePago = metodoPagoService.obtenerTodos();

        if (metodosDePago.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodosDePago,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMetodoPagoPorId(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(metodoPagoService.buscarPorId(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Metodo pago no encontrado",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarMetodoPago(@Valid @RequestBody MetodoPago metodoPago){
        try {
            return new ResponseEntity<>(metodoPagoService.guardarMetodoPago(metodoPago),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear metodo de pago",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actulizarMetodoPago(@PathVariable Integer id,@Valid @RequestBody MetodoPago metodoPago){
        try {
            return new ResponseEntity<>(metodoPagoService.actualizarMetodoPago(id, metodoPago),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMetodoPago(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(metodoPagoService.eliminarMetodoPAgo(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar Metodo de pago",HttpStatus.BAD_REQUEST);
        }
    }
}
