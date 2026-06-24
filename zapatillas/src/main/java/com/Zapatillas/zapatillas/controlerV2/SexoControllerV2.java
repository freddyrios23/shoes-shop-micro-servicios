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
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<SexoDTO>> getAllSexo() {
        List<EntityModel<SexoDTO>> sexos = sexoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sexos,
                linkTo(methodOn(SexoControllerV2.class).getAllSexo()).withSelfRel());
    }

    @Operation(
        summary = "Buscar sexo",
        description = "busca el sexo de la zapatilla por su id"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        public EntityModel<SexoDTO> getSexobyId(@PathVariable Integer id){
            SexoDTO sexo = sexoService.buscarPorId(id);
            return assembler.toModel(sexo);
    }

    @Operation(
        summary = "Registra sexo",
        description = "Registra el sexo de la zapatilla en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        public ResponseEntity<EntityModel<SexoDTO>> createSexo(@RequestBody Sexo sexo){
            Sexo newSexo = sexoService.guardarSexo(sexo);
            SexoDTO sexoDTO = sexoService.buscarPorId(newSexo.getId());

            return ResponseEntity
                    .created(linkTo(methodOn(SexoControllerV2.class).getSexobyId(newSexo.getId())).toUri())
                    .body(assembler.toModel(sexoDTO));
    }

    @Operation(
        summary = "Actualizar sexp",
        description = "Edita el sexo de la zapatilla por su id"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> updateZapatilla(@PathVariable Integer id, @RequestBody Sexo sexo) {
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
    public ResponseEntity<?> deleteSexo(@PathVariable Integer id){
        sexoService.eliminarSexo(id);
        return ResponseEntity.noContent().build();
    }
}
