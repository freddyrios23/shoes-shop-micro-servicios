package com.Zapatillas.zapatillas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Zapatillas.zapatillas.DTO.MarcaDTO;
import com.Zapatillas.zapatillas.controlerV2.MarcaControllerV2;

@Component
public class MarcaModelAssembler implements RepresentationModelAssembler<MarcaDTO,EntityModel<MarcaDTO>>{

    @Override
    public EntityModel<MarcaDTO> toModel(MarcaDTO marca) {
        return EntityModel.of(marca,
                linkTo(methodOn(MarcaControllerV2.class).porId(marca.getId())).withSelfRel(),
                linkTo(methodOn(MarcaControllerV2.class).todas()).withRel("marcas")
        );
    }
}
