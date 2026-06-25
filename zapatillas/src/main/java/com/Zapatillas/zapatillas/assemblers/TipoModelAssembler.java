package com.Zapatillas.zapatillas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Zapatillas.zapatillas.DTO.TipoDTO;
import com.Zapatillas.zapatillas.controlerV2.TipoControllerV2;

@Component
public class TipoModelAssembler implements RepresentationModelAssembler<TipoDTO,EntityModel<TipoDTO>>{

    @Override
    public EntityModel<TipoDTO> toModel(TipoDTO tipo) {
        return EntityModel.of(tipo,
            linkTo(methodOn(TipoControllerV2.class).porId(tipo.getId())).withSelfRel(),
                linkTo(methodOn(TipoControllerV2.class).todos()).withRel("tipos")
        );
    }
}
