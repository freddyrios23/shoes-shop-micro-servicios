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

import com.Zapatillas.zapatillas.DTO.MarcaDTO;
import com.Zapatillas.zapatillas.assemblers.MarcaModelAssembler;
import com.Zapatillas.zapatillas.model.Marca;
import com.Zapatillas.zapatillas.service.MarcaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Marcas",description = "Endpoints para gestionar las marcas de zapatillas")
@RestController
@RequestMapping("/api/v2/marcas")
public class MarcaControllerV2 {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MarcaModelAssembler assembler;

    @Operation(
        summary = "Listar marcas",
        description = "Obtiene todas las marcas registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MarcaDTO>>> todas() {
        List<EntityModel<MarcaDTO>> marcas = marcaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                marcas,
                linkTo(methodOn(MarcaControllerV2.class).todas()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Buscar marca por ID",
        description = "Obtiene una marca específica según su id"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> porId(@PathVariable Integer id) {
        try {
            MarcaDTO dto = marcaService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Crear marca",
        description = "Registra una nueva marca de zapatillas"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> registrar(@Valid @RequestBody Marca marca) {
        try {
            MarcaDTO newMarca = marcaService.guardarMarca(marca);
            return ResponseEntity
                    .created(linkTo(methodOn(MarcaControllerV2.class).porId(newMarca.getId())).toUri())
                    .body(assembler.toModel(newMarca));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Actualizar marca",
        description = "Actualiza los datos de una marca existente"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> updateTipo(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            Marca marcaActualizada = marcaService.actualizarMarca(id, marca);

            MarcaDTO marcaDTO = marcaService.buscarPorId(marcaActualizada.getId());

            return ResponseEntity.ok(assembler.toModel(marcaDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Eliminar marca",
        description = "Elimina una marca según su id"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteMarca(@PathVariable Integer id){
        marcaService.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }
}