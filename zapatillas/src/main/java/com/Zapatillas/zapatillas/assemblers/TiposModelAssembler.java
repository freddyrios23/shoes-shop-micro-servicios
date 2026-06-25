package com.Zapatillas.zapatillas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Zapatillas.zapatillas.controlerV2.TiposControllerV2;
import com.Zapatillas.zapatillas.model.Tipos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TiposModelAssembler implements RepresentationModelAssembler<Tipos,EntityModel<Tipos>>{

    @Override
    public EntityModel<Tipos> toModel(Tipos tipo) {
        return EntityModel.of(tipo,
                linkTo(methodOn(TiposControllerV2.class).todos()).withRel("tipos_zapatillas")
        );
    }
}