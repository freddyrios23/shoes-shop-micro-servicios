package com.Zapatillas.zapatillas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.service.ZapatillaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Zapatillas", description = "Endpoints para gestion zapatillas")
@RestController
@RequestMapping("/api/v1/zapatillas")
public class ZapatillaController {
    
    @Autowired
    private ZapatillaService zapatillaService;

    @Operation(
        summary = "Listar Zapatillas",
        description = "Obtiene todas las zapatillas que stan registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<?> todasLasZapatillas() {
        List<ZapatillaDTO> zapatillas = zapatillaService.obtenerTodas();

        if (zapatillas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<ZapatillaDTO>> recursos = convertirListaAHateoas(zapatillas);

        return new ResponseEntity<>(recursos, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar Zapatilla",
        description = "Obtiene La zapatilla por un id especifico"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarZapatillaPorId(@PathVariable Integer id) {
        try {
            ZapatillaDTO zapatilla = zapatillaService.buscarPorId(id);
            EntityModel<ZapatillaDTO> recurso = convertirAHateoas(zapatilla);
            return new ResponseEntity<>(recurso, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontró la zapatilla", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Agregar Zapatilla",
        description = "Registra una nueva zapatilla en el sistema"
    )
    @PostMapping
    public ResponseEntity<?> agregarZapatilla(@Valid @RequestBody Zapatilla zapatilla) {
        try {
            Zapatilla nuevaZapatilla = zapatillaService.guardarZapatilla(zapatilla);

            ZapatillaDTO zapatillaDTO = zapatillaService.buscarPorId(nuevaZapatilla.getId());
            EntityModel<ZapatillaDTO> recurso = convertirAHateoas(zapatillaDTO);

            return new ResponseEntity<>(recurso, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se guardó la zapatilla", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Editar Zapatilla",
        description = "Edita una zapatilla por un id especifico"
    )    
    @PatchMapping("/{id}")
    public ResponseEntity<?> editarZapatilla(@PathVariable Integer id, @RequestBody Zapatilla zapatilla) {
        try {
            Zapatilla editada = zapatillaService.actualizarZapatilla(id, zapatilla);

            ZapatillaDTO zapatillaDTO = zapatillaService.buscarPorId(editada.getId());
            EntityModel<ZapatillaDTO> recurso = convertirAHateoas(zapatillaDTO);

            return new ResponseEntity<>(recurso, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Zapatilla no encontrada", HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        summary = "Elmina Zapatilla",
        description = "elimina una zapatilla por un id especifico"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarZapatilla(@PathVariable Integer id){

        String resultado = zapatillaService.eliminarZapatilla(id);

        if(resultado.contains("exitosamente")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }


    private EntityModel<ZapatillaDTO> convertirAHateoas(ZapatillaDTO zapatilla) {
        EntityModel<ZapatillaDTO> recurso = EntityModel.of(zapatilla);

        recurso.add(Link.of("/api/v1/zapatillas/" + zapatilla.getId()).withSelfRel());
        recurso.add(Link.of("/api/v1/zapatillas").withRel("zapatillas"));

        return recurso;
    }

    private CollectionModel<EntityModel<ZapatillaDTO>> convertirListaAHateoas(List<ZapatillaDTO> zapatillas) {
        List<EntityModel<ZapatillaDTO>> recursos = zapatillas.stream()
                .map(this::convertirAHateoas)
                .toList();

        return CollectionModel.of(recursos,
                Link.of("/api/v1/zapatillas").withSelfRel()
        );
    }
}
