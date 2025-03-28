package com.example.demo.model.Unidade;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.controller.UnidadeController;

@Component
public class UnidadeModelAssembler
        implements RepresentationModelAssembler<ObterUnidadeDTO, EntityModel<ObterUnidadeDTO>> {

    @Override
    @NonNull
    public EntityModel<ObterUnidadeDTO> toModel(@NonNull ObterUnidadeDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UnidadeController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UnidadeController.class).getAll(Pageable.unpaged())).withRel("unidades"));
    }

}
