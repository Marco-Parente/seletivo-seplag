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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Unidade.CriarUnidadeDTO;
import com.example.demo.model.Unidade.ObterUnidadeDTO;
import com.example.demo.model.Unidade.Unidade;
import com.example.demo.model.Unidade.UnidadeMapper;
import com.example.demo.model.Unidade.UnidadeModelAssembler;
import com.example.demo.repository.UnidadeRepository;

import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/unidade")
public class UnidadeController {

    @Autowired
    private UnidadeRepository repository;

    @Autowired
    private UnidadeMapper unidadeMapper;

    @Autowired
    private UnidadeModelAssembler assembler;

    @GetMapping
    @PageableAsQueryParam
    public CollectionModel<EntityModel<ObterUnidadeDTO>> getAll(@Parameter(hidden = true) Pageable pageable) {
        List<EntityModel<ObterUnidadeDTO>> unidades = repository.findAll(pageable).stream()
                .map(unidadeMapper::toObterUnidadeDTO)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(unidades, linkTo(methodOn(UnidadeController.class).getAll(pageable)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObterUnidadeDTO> getById(@PathVariable Integer id) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(unidadeMapper.toObterUnidadeDTO(unidade));
    }

    @PostMapping
    public Unidade create(@RequestBody CriarUnidadeDTO criarUnidadeDto) {
        var u = unidadeMapper.toUnidade(criarUnidadeDto);
        return repository.save(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}