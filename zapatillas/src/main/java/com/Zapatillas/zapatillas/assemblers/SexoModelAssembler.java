package com.Zapatillas.zapatillas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.Zapatillas.zapatillas.DTO.SexoDTO;
import com.Zapatillas.zapatillas.controlerV2.SexoControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class SexoModelAssembler implements RepresentationModelAssembler<SexoDTO,EntityModel<SexoDTO>>{

    @Override
    public EntityModel<SexoDTO> toModel(SexoDTO sexo) {
        return EntityModel.of(sexo,
                linkTo(methodOn(SexoControllerV2.class).getSexobyId(sexo.getId())).withSelfRel(),
                linkTo(methodOn(SexoControllerV2.class).getAllSexo()).withRel("sexos")
        );
    }
}
