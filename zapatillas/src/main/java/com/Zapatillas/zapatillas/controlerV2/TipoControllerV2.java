package com.Zapatillas.zapatillas.controlerV2;

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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Zapatillas.zapatillas.DTO.TipoDTO;
import com.Zapatillas.zapatillas.assemblers.TipoModelAssembler;
import com.Zapatillas.zapatillas.model.Tipo;
import com.Zapatillas.zapatillas.service.TipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tipos",description = "Endpoints para gestionas los tipos de zapatillas")
@RestController
@RequestMapping("/api/v2/tipos")
public class TipoControllerV2 {

    @Autowired
    private TipoService tipoService;

    @Autowired 
    private TipoModelAssembler assembler;

    @Operation(
        summary = "Listar tipos",
        description = "Obtiene todos los tipos de zapatillas que stan registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TipoDTO>>> todos() {
        List<EntityModel<TipoDTO>> tipos = tipoService.obtenertodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tipos,
                linkTo(methodOn(TiposControllerV2.class).todos()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Buscar tipo de zapatilla",
        description = "Obtiene el tipo de zapatilla segun su id"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDTO>> porId(@PathVariable Integer id) {
        try {
            TipoDTO dto = tipoService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Resgistra el tipo",
        description = "agrega el tipo de zapatilla al sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDTO>> registrar(@Valid @RequestBody Tipo tipo) {
        try {
            TipoDTO newTipo = tipoService.guardaTipo(tipo);
            return ResponseEntity
                    .created(linkTo(methodOn(TipoControllerV2.class).porId(newTipo.getId())).toUri())
                    .body(assembler.toModel(newTipo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Edita el tipo",
        description = "Edita el tipo de zapatilla por su id"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDTO>> updateTipo(@PathVariable Integer id, @RequestBody Tipo tipo) {
        try {
            Tipo tipoActualizado = tipoService.actualizarTipo(id, tipo);

            TipoDTO tipoDTO = tipoService.buscarPorId(tipoActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(tipoDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Elimina el tipo",
        description = "Elimina del sistema el tipo de zapatilla"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteTipo(@PathVariable Integer id){
        tipoService.eliminarTipo(id);
        return ResponseEntity.noContent().build();
    }
}