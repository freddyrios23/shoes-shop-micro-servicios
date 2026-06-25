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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zapatillas.zapatillas.DTO.MaterialDTO;
import com.Zapatillas.zapatillas.assemblers.MaterialModelAssembler;
import com.Zapatillas.zapatillas.model.Material;
import com.Zapatillas.zapatillas.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "materiales",description = "Endpoints para gestionar el material de la zapatilla")
@RestController
@RequestMapping("/api/v2/material")
public class MaterialControllerV2 {
    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialModelAssembler assembler;

    @Operation(
        summary = "Listar materiales",
        description = "Obtiene todos los materiales registrados en el sistema"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MaterialDTO>>> todos() {
        List<EntityModel<MaterialDTO>> material = materialService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (material.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                material,
                linkTo(methodOn(MaterialControllerV2.class).todos()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Buscar material",
        description = "busca el mateial de la zapatilla por su id"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialDTO>> porId(@PathVariable Integer id) {
        try {
            MaterialDTO dto = materialService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Registra el material",
        description = "Agrega el material de la zapatilla al sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialDTO>> registrar(@Valid @RequestBody Material material) {
        try {
            MaterialDTO newMaterial = materialService.guardarMaterial(material);
            return ResponseEntity
                    .created(linkTo(methodOn(MaterialControllerV2.class).porId(newMaterial.getId())).toUri())
                    .body(assembler.toModel(newMaterial));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Edita material",
        description = "Actualiza el matrial de la zapatilla por su id"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialDTO>> updateMaterial(@PathVariable Integer id, @RequestBody Material material) {
        try {
            Material materialActualizado = materialService.actualizarMaterial(id, material);

            MaterialDTO materialDTO = materialService.buscarPorId(materialActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(materialDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Elimina material",
        description = "Elimina el material de la zapatilla por su id"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteMaterial(@PathVariable Integer id){
        materialService.eliminarMaterial(id);
        return ResponseEntity.noContent().build();
    }

}

