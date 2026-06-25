package com.Zapatillas.zapatillas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.controlerV2.ZapatillaControllerV2;

@Component
public class ZapatillaModelAssembler implements RepresentationModelAssembler<ZapatillaDTO, EntityModel<ZapatillaDTO>> {

    @Override
    public EntityModel<ZapatillaDTO> toModel(ZapatillaDTO zapatilla) {
        return EntityModel.of(zapatilla,
                linkTo(methodOn(ZapatillaControllerV2.class).porId(zapatilla.getId())).withSelfRel(),
                linkTo(methodOn(ZapatillaControllerV2.class).todas()).withRel("zapatillas")
        );
    }
}