package com.boletas20.boletas20.Controllerv2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.boletas20.boletas20.Assemblers.MetodoPagoModelAssembler;
import com.boletas20.boletas20.DTO.MetodoPagoDTO;
import com.boletas20.boletas20.Model.MetodoPago;
import com.boletas20.boletas20.Service.MetodoPagoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public class MetodoPagoControllerV2 {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private MetodoPagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MetodoPagoDTO>>> todas() {
        List<EntityModel<MetodoPagoDTO>> pagos = metodoPagoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                linkTo(methodOn(MetodoPagoControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoPagoDTO>> porId(@PathVariable Integer id) {
        try {
            MetodoPagoDTO metodoPago = metodoPagoService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(metodoPago));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoPagoDTO>> registrar(@Valid @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPagoDTO newMetodoPagoDTO = metodoPagoService.guardarMetodoPago(metodoPago);
            return ResponseEntity
                    .created(linkTo(methodOn(MetodoPagoControllerV2.class).porId(newMetodoPagoDTO.getId())).toUri())
                    .body(assembler.toModel(newMetodoPagoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoPagoDTO>> actualizarMetodoPago(@PathVariable Integer id, @Valid @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPagoDTO editado = metodoPagoService.actualizarMetodoPago(id, metodoPago);
            return ResponseEntity.ok(assembler.toModel(editado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMetodoPago(@PathVariable Integer id) {
        String resultado = metodoPagoService.eliminarMetodoPAgo(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
