package com.Zapatillas.zapatillas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Zapatillas.zapatillas.DTO.SexoDTO;
import com.Zapatillas.zapatillas.controlerV2.SexoControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SexoModelAssembler implements RepresentationModelAssembler<SexoDTO,EntityModel<SexoDTO>>{

    @Override
    public EntityModel<SexoDTO> toModel(SexoDTO sexo) {
        return EntityModel.of(sexo,
                linkTo(methodOn(SexoControllerV2.class).porId(sexo.getId())).withSelfRel(),
                linkTo(methodOn(SexoControllerV2.class).todos()).withRel("sexos")
        );
    }
}
