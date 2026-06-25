package com.Zapatillas.zapatillas.controlerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zapatillas.zapatillas.assemblers.ColoresModelAssembler;
import com.Zapatillas.zapatillas.model.Colores;
import com.Zapatillas.zapatillas.service.ColoresService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Colores",description = "Endpoints para gestionar los colores de las zapatillas")
@RestController
@RequestMapping("/api/v2/colores")
public class ColoresControllerV2 {
    
    @Autowired
    private ColoresService coloresService;

    @Autowired
    private ColoresModelAssembler assembler;

    @Operation(
        summary = "Listar colores asociados a zapatillas"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Colores>>> todos() {
        List<EntityModel<Colores>> colores = coloresService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (colores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                colores,
                linkTo(methodOn(ColoresControllerV2.class).todos()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Asociar color a zapatilla"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<String>> agregarRelacion(@RequestBody Colores relacion) {
        try {
            String resultado = coloresService.guardarRelacion(relacion);

            EntityModel<String> recurso = EntityModel.of(resultado,
                    linkTo(methodOn(ColoresControllerV2.class).todos()).withRel("color_zapatillas")
            );

            return ResponseEntity
                    .created(linkTo(methodOn(ColoresControllerV2.class).todos()).toUri())
                    .body(recurso);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}