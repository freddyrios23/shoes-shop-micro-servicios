package com.Zapatillas.zapatillas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Zapatillas.zapatillas.DTO.ColorDTO;
import com.Zapatillas.zapatillas.controlerV2.ColorControllerV2;

@Component
public class ColorModelAssembler implements RepresentationModelAssembler<ColorDTO,EntityModel<ColorDTO>>{

    @Override
    public EntityModel<ColorDTO> toModel(ColorDTO color) {
        return EntityModel.of(color,
                linkTo(methodOn(ColorControllerV2.class).porId(color.getId())).withSelfRel(),
                linkTo(methodOn(ColorControllerV2.class).todas()).withRel("colores")
        );
    }
}
