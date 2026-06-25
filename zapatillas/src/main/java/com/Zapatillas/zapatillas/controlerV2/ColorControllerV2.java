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

import com.Zapatillas.zapatillas.DTO.ColorDTO;
import com.Zapatillas.zapatillas.assemblers.ColorModelAssembler;
import com.Zapatillas.zapatillas.model.Color;
import com.Zapatillas.zapatillas.service.ColorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Colores",description = "Endpoints para gestionar los colores disponibles de las zapatillas")
@RestController
@RequestMapping("/api/v2/color")
public class ColorControllerV2 {
    @Autowired
    private ColorService colorService;

    @Autowired
    private ColorModelAssembler assembler;

    @Operation(
        summary = "Listar colores",
        description = "Obtiene todos los colores registrados en el sistema"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ColorDTO>>> todas() {
        List<EntityModel<ColorDTO>> colores = colorService.obtenerTodos().stream()
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
        summary = "Buscar color por ID",
        description = "Obtiene un color específico según su identificador"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColorDTO>> porId(@PathVariable Integer id) {
        try {
            ColorDTO dto = colorService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Crear color",
        description = "Registra un nuevo color disponible para las zapatillas"
    )    
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColorDTO>> registrar(@Valid @RequestBody Color color) {
        try {
            ColorDTO newColor = colorService.guardarColor(color);
            return ResponseEntity
                    .created(linkTo(methodOn(ColorControllerV2.class).porId(newColor.getId())).toUri())
                    .body(assembler.toModel(newColor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Actualizar color",
        description = "Actualiza los datos de un color existente"
    )    
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColorDTO>> updateColor(@PathVariable Integer id, @RequestBody Color color) {
        try {
            Color colorActualizado = colorService.actualizarColor(id, color);

            ColorDTO colorDTO = colorService.buscarPorId(colorActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(colorDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Eliminar color",
        description = "Elimina un color según su identificador"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteColor(@PathVariable Integer id){
        colorService.eliminarColor(id);
        return ResponseEntity.noContent().build();
    }

}
