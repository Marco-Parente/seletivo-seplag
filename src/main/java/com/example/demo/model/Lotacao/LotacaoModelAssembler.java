package com.example.demo.model.Lotacao;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

// import com.example.demo.controller.LotacaoController;

// @Component
// public class LotacaoModelAssembler implements RepresentationModelAssembler<ObterLotacaoDTO, EntityModel<ObterLotacaoDTO>> {

//     @Override
//     public EntityModel<ObterLotacaoDTO> toModel(ObterLotacaoDTO lotacao) {
//         return EntityModel.of(lotacao,
//                 linkTo(methodOn(LotacaoController.class).getById(lotacao.getId())).withSelfRel(),
//                 linkTo(methodOn(LotacaoController.class).getAll()).withRel("lotacoes"));
//     }
// }