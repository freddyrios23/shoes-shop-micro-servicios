package com.boletas20.boletas20.Controllerv2;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boletas20.boletas20.Assemblers.BoletaModelAssembler;

import com.boletas20.boletas20.DTO.BoletaDTO;
import com.boletas20.boletas20.Model.Boleta;
import com.boletas20.boletas20.Service.BoletaService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Colores",description = "Endpoints para gestionar las boletas de las zapatillas")
@RestController("BoletaControllerV2")
@RequestMapping("/api/v2/boleta")
public class BoletaControllerV2 {

@Autowired
    private BoletaService boletaService;

    @Autowired
    private BoletaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<BoletaDTO>>> todas() {
    List<EntityModel<BoletaDTO>> boletas = boletaService.obtenerTodas().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                boletas,
                linkTo(methodOn(BoletaControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> porId(@PathVariable Integer id) {
        try {
            BoletaDTO dto = boletaService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> registrar(@Valid @RequestBody Boleta boleta) {
        try {
            // El servicio ahora sí devuelve un BoletaDTO de forma correcta
            BoletaDTO newBoletaDTO = boletaService.guardarBoleta(boleta); 
            
            return ResponseEntity
                    .created(linkTo(methodOn(BoletaControllerV2.class).porId(newBoletaDTO.getId())).toUri())
                    .body(assembler.toModel(newBoletaDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
