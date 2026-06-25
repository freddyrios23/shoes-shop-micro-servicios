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

import com.Zapatillas.zapatillas.assemblers.MaterialesModelAssembler;
import com.Zapatillas.zapatillas.model.Materiales;
import com.Zapatillas.zapatillas.service.MaterialesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Materiales de zapatilla",description = "Endpoint para asociar material con zapatillas")
@RestController
@RequestMapping("/api/v2/materiales")
public class MaterialesControllerV2 {
    @Autowired
    private MaterialesService materialesService;

    @Autowired
    private MaterialesModelAssembler assembler;

    @Operation(
        summary = "Listar materiales asociados a zapatillas"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Materiales>>> todos() {
        List<EntityModel<Materiales>> materiales = materialesService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (materiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                materiales,
                linkTo(methodOn(MaterialesControllerV2.class).todos()).withSelfRel()
        ));
    }
    @Operation(
        summary = "Asociar material a zapatillas"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<String>> agregarRelacion(@RequestBody Materiales relacion) {
        try {
            String resultado = materialesService.guardarRelacion(relacion);

            EntityModel<String> recurso = EntityModel.of(resultado,
                    linkTo(methodOn(MaterialesControllerV2.class).todos()).withRel("material_zapatillas")
            );

            return ResponseEntity
                    .created(linkTo(methodOn(MaterialesControllerV2.class).todos()).toUri())
                    .body(recurso);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}