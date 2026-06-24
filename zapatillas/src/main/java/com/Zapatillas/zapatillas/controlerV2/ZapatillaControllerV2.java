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

import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.assemblers.ZapatillaModelAssembler;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.service.ZapatillaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Zapatillas", description = "Endpoints para gestion zapatillas")
@RestController
@RequestMapping("/api/v2/zapatillas")
public class ZapatillaControllerV2 {
    
    @Autowired
    private ZapatillaService zapatillaService;

    @Autowired
    private ZapatillaModelAssembler assembler;

    @Operation(
        summary = "Listar Zapatillas",
        description = "Obtiene todas las zapatillas que stan registradas en el sistema"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ZapatillaDTO>> getAllZapatillas() {
        List<EntityModel<ZapatillaDTO>> zapatillas = zapatillaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(zapatillas,
                linkTo(methodOn(ZapatillaControllerV2.class).getAllZapatillas()).withSelfRel());
    }

    @Operation(
        summary = "Buscar Zapatilla",
        description = "Obtiene La zapatilla por un id especifico"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ZapatillaDTO> getZapatillabyId(@PathVariable Integer id){
        ZapatillaDTO zapatilla = zapatillaService.buscarPorId(id);
        return assembler.toModel(zapatilla);
    }

    @Operation(
        summary = "Agregar Zapatilla",
        description = "Registra una nueva zapatilla en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ZapatillaDTO>> createZapatilla(@RequestBody Zapatilla zapatilla){
        Zapatilla newZapatilla = zapatillaService.guardarZapatilla(zapatilla);
        ZapatillaDTO zapatillaDTO = zapatillaService.buscarPorId(newZapatilla.getId());

        return ResponseEntity
                .created(linkTo(methodOn(ZapatillaControllerV2.class).getZapatillabyId(newZapatilla.getId())).toUri())
                .body(assembler.toModel(zapatillaDTO));
    }

    @Operation(
        summary = "Editar Zapatilla",
        description = "Edita una zapatilla por un id especifico"
    )    
    
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ZapatillaDTO>> updateZapatilla(@PathVariable Integer id, @RequestBody Zapatilla zapatilla) {
        try {
            Zapatilla zapatillaActualizada = zapatillaService.actualizarZapatilla(id, zapatilla);

            ZapatillaDTO zapatillaDTO = zapatillaService.buscarPorId(zapatillaActualizada.getId());

            return ResponseEntity.ok(assembler.toModel(zapatillaDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(
        summary = "Elmina Zapatilla",
        description = "elimina una zapatilla por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteZapatilla(@PathVariable Integer id){
        zapatillaService.eliminarZapatilla(id);
        return ResponseEntity.noContent().build();
    }
}
