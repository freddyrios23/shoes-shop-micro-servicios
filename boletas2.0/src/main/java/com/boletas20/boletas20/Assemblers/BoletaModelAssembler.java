package com.boletas20.boletas20.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.boletas20.boletas20.Controllerv2.BoletaControllerV2;
import com.boletas20.boletas20.DTO.BoletaDTO;

@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<BoletaDTO, EntityModel<BoletaDTO>>{

    @Override
        public EntityModel<BoletaDTO> toModel(BoletaDTO boleta) {
             return EntityModel.of(boleta,
                linkTo(methodOn(BoletaControllerV2.class).porId(boleta.getId())).withSelfRel(),
                linkTo(methodOn(BoletaControllerV2.class).todas()).withRel("boletas")
            );
        }
}
