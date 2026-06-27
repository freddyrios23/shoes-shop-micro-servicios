package com.boletas20.boletas20.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.boletas20.boletas20.Controllerv2.MetodoEnvioControllerV2;
import com.boletas20.boletas20.DTO.MetodoEnvioDTO;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MetodoEnvioModelAssembler implements RepresentationModelAssembler<MetodoEnvioDTO, EntityModel<MetodoEnvioDTO>> {
@Override
    public EntityModel<MetodoEnvioDTO> toModel(MetodoEnvioDTO metodoEnvio) {
        return EntityModel.of(metodoEnvio,
                linkTo(methodOn(MetodoEnvioControllerV2.class).porId(metodoEnvio.getId())).withSelfRel(),
                linkTo(methodOn(MetodoEnvioControllerV2.class).todas()).withRel("metodos_envio")
        );
    }
}
