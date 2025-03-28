package com.example.demo.model.ServidorEfetivo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.controller.ServidorEfetivoController;

@Component
public class ServidorEfetivoModelAssembler implements RepresentationModelAssembler<ObterServidorEfetivoDTO, EntityModel<ObterServidorEfetivoDTO>> {

    @Override
    @NonNull
    public EntityModel<ObterServidorEfetivoDTO> toModel(@NonNull ObterServidorEfetivoDTO servidorEfetivo) {
        return EntityModel.of(servidorEfetivo,
                linkTo(methodOn(ServidorEfetivoController.class).getById(servidorEfetivo.getId())).withSelfRel(),
                linkTo(methodOn(ServidorEfetivoController.class).getAll(Pageable.unpaged())).withRel("servidoresEfetivos"));
    }
}