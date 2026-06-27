package com.boletas20.boletas20.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.RepresentationModelAssembler;


import com.boletas20.boletas20.Controllerv2.MetodoPagoControllerV2;
import com.boletas20.boletas20.DTO.MetodoPagoDTO;

@Component
public class MetodoPagoModelAssembler  implements RepresentationModelAssembler<MetodoPagoDTO, EntityModel<MetodoPagoDTO>> {
    @Override
    public EntityModel<MetodoPagoDTO> toModel(MetodoPagoDTO metodoPago) {
        return EntityModel.of(metodoPago,
                linkTo(methodOn(MetodoPagoControllerV2.class).porId(metodoPago.getId())).withSelfRel(),
                linkTo(methodOn(MetodoPagoControllerV2.class).todas()).withRel("metodos_pago")
        );
    }

}
