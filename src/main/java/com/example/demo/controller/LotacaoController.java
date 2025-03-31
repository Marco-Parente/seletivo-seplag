package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Lotacao.CriarLotacaoDTO;
import com.example.demo.model.Lotacao.ObterLotacaoDTO;
import com.example.demo.model.Lotacao.Lotacao;
import com.example.demo.model.Lotacao.LotacaoMapper;
import com.example.demo.model.Lotacao.LotacaoModelAssembler;
import com.example.demo.repository.LotacaoRepository;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/lotacao")
public class LotacaoController {

    @Autowired
    private LotacaoRepository repository;

    @Autowired
    private LotacaoMapper lotacaoMapper;

    @Autowired
    private LotacaoModelAssembler assembler;

    @GetMapping
    @PageableAsQueryParam
    public CollectionModel<EntityModel<ObterLotacaoDTO>> getAll(@Parameter(hidden = true) Pageable pageable) {
        List<EntityModel<ObterLotacaoDTO>> lotacoes = repository.findAll(pageable).stream()
                .map(lotacaoMapper::toObterLotacaoDTO)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lotacoes, linkTo(methodOn(LotacaoController.class).getAll(pageable)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObterLotacaoDTO> getById(@PathVariable Integer id) {
        Lotacao lotacao = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(lotacaoMapper.toObterLotacaoDTO(lotacao));
    }

    @PostMapping
    public EntityModel<ObterLotacaoDTO> create(@RequestBody CriarLotacaoDTO criarLotacaoDto) throws Exception {
        var l = lotacaoMapper.toLotacao(criarLotacaoDto);

        var lotacaoExistente = repository.findByUnidade_UnidIdAndPessoa_PesId(
                criarLotacaoDto.getUnidadeId(), criarLotacaoDto.getPessoaId());

        if (lotacaoExistente != null) {
            throw new Exception("O servidor já está em uma lotação no momento");
        }

        return assembler.toModel(lotacaoMapper.toObterLotacaoDTO(repository.save(l)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}