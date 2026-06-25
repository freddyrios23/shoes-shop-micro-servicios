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

import com.Zapatillas.zapatillas.DTO.SexoDTO;
import com.Zapatillas.zapatillas.assemblers.SexoModelAssembler;
import com.Zapatillas.zapatillas.model.Sexo;
import com.Zapatillas.zapatillas.service.SexoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Sexos",description = "Endpoints para gestionar el sexo de la zapatilla")
@RestController
@RequestMapping("/api/v2/sexos")
public class SexoControllerV2 {
    
    @Autowired
    private SexoService sexoService;

    @Autowired 
    private SexoModelAssembler assembler;

    @Operation(
        summary = "Listar sexos",
        description = "Obtiene todos los sexos de zapatillas que estan registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SexoDTO>>> todos() {
        List<EntityModel<SexoDTO>> sexos = sexoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (sexos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                sexos,
                linkTo(methodOn(SexoControllerV2.class).todos()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Buscar sexo",
        description = "busca el sexo de la zapatilla por su id"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> porId(@PathVariable Integer id) {
        try {
            SexoDTO dto = sexoService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Registra sexo",
        description = "Registra el sexo de la zapatilla en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> registrar(@Valid @RequestBody Sexo sexo) {
        try {
            SexoDTO newSexo = sexoService.guardarSexo(sexo);
            return ResponseEntity
                    .created(linkTo(methodOn(SexoControllerV2.class).porId(newSexo.getId())).toUri())
                    .body(assembler.toModel(newSexo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Actualizar sexp",
        description = "Edita el sexo de la zapatilla por su id"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> updateTipo(@PathVariable Integer id, @RequestBody Sexo sexo) {
        try {
            Sexo sexoActualizado = sexoService.actualizarSexo(id, sexo);

            SexoDTO sexoDTO = sexoService.buscarPorId(sexoActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(sexoDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Eliminar sexo",
        description = "Elimina del sistema el sexo de la zapatilla"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteSe(@PathVariable Integer id){
        sexoService.eliminarSexo(id);
        return ResponseEntity.noContent().build();
    }
}

