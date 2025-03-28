package com.example.demo.model.ServidorTemporario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.controller.ServidorTemporarioController;

@Component
public class ServidorTemporarioModelAssembler implements RepresentationModelAssembler<ObterServidorTemporarioDTO, EntityModel<ObterServidorTemporarioDTO>> {

    @Override
    @NonNull
    public EntityModel<ObterServidorTemporarioDTO> toModel(@NonNull ObterServidorTemporarioDTO servidorTemporario) {
        return EntityModel.of(servidorTemporario,
                linkTo(methodOn(ServidorTemporarioController.class).getById(servidorTemporario.getId())).withSelfRel(),
                linkTo(methodOn(ServidorTemporarioController.class).getAll()).withRel("servidoresTemporarios"));
    }
}