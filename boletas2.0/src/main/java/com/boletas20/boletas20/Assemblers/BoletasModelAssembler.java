package com.boletas20.boletas20.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.boletas20.boletas20.Controllerv2.BoletasControllerV2;
import com.boletas20.boletas20.Model.Boletas;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BoletasModelAssembler implements RepresentationModelAssembler<Boletas, EntityModel<Boletas>> {
    @Override
   public EntityModel<Boletas> toModel(Boletas boletas) {
    return EntityModel.of(boletas,

            linkTo(methodOn(BoletasControllerV2.class).todas()).withRel("boletas_boleta")
        );
    }
}
