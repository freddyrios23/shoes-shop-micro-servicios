package com.boletas20.boletas20.Controllerv2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boletas20.boletas20.Assemblers.MetodoEnvioModelAssembler;
import com.boletas20.boletas20.DTO.MetodoEnvioDTO;
import com.boletas20.boletas20.Model.MetodoEnvio;
import com.boletas20.boletas20.Service.MetodoEnvioService;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController("MetodoEnvioControllerV2")
@RequestMapping("/api/v2/metodo-envio")
public class MetodoEnvioControllerV2 {
 
    @Autowired
    private MetodoEnvioService metodoEnvioService;

    @Autowired
    private MetodoEnvioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MetodoEnvioDTO>>> todas() {
        List<EntityModel<MetodoEnvioDTO>> envios = metodoEnvioService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                envios,
                linkTo(methodOn(MetodoEnvioControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> porId(@PathVariable Integer id) {
        try {
            MetodoEnvioDTO envio = metodoEnvioService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(envio));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> registrar(@Valid @RequestBody MetodoEnvio metodoEnvio) {
        try {
            MetodoEnvioDTO newMetodoDTO = metodoEnvioService.guardarMetodoEnvio(metodoEnvio);
            return ResponseEntity
                    .created(linkTo(methodOn(MetodoEnvioControllerV2.class).porId(newMetodoDTO.getId())).toUri())
                    .body(assembler.toModel(newMetodoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> editarEnvio(@PathVariable Integer id, @Valid @RequestBody MetodoEnvio metodoEnvio) {
        try {
            MetodoEnvioDTO editada = metodoEnvioService.actualizarMetodoEnvio(id, metodoEnvio);
            return ResponseEntity.ok(assembler.toModel(editada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMetodoEnvio(@PathVariable Integer id) {
        String resultado = metodoEnvioService.eliminarMetodoEnvio(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
