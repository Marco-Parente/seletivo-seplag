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
import com.example.demo.model.Cidade;
import com.example.demo.model.UnidadeEndereco;
import com.example.demo.model.Endereco.Endereco;
import com.example.demo.model.Unidade.CriarUnidadeDTO;
import com.example.demo.model.Unidade.ObterUnidadeDTO;
import com.example.demo.model.Unidade.Unidade;
import com.example.demo.model.Unidade.UnidadeMapper;
import com.example.demo.model.Unidade.UnidadeModelAssembler;
import com.example.demo.repository.CidadeRepository;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.UnidadeEnderecoRepository;
import com.example.demo.repository.UnidadeRepository;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/unidade")
public class UnidadeController {

    @Autowired
    private UnidadeRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UnidadeEnderecoRepository unidadeEnderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

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
        u = repository.save(u);

        if (criarUnidadeDto.getEndereco() != null) {
            Endereco endereco = new Endereco();
            endereco.setEndLogradouro(criarUnidadeDto.getEndereco().getLogradouro());
            endereco.setEndTipoLogradouro(criarUnidadeDto.getEndereco().getTipoLogradouro());
            endereco.setEndNumero(criarUnidadeDto.getEndereco().getNumero());
            endereco.setEndBairro(criarUnidadeDto.getEndereco().getBairro());

            if (criarUnidadeDto.getEndereco().getCidade() != null) {
                Cidade cidade;
                if (criarUnidadeDto.getEndereco().getCidade().getId() != null) {
                    cidade = cidadeRepository.findById(Integer.parseInt(criarUnidadeDto.getEndereco().getCidade().getId()))
                            .orElseGet(() -> {
                                var novaCidade = new Cidade();
                                novaCidade.setCidNome(criarUnidadeDto.getEndereco().getCidade().getNome());
                                novaCidade.setCidUf(criarUnidadeDto.getEndereco().getCidade().getUf());
                                return cidadeRepository.save(novaCidade);
                            });
                } else {
                    cidade = new Cidade();
                    cidade.setCidNome(criarUnidadeDto.getEndereco().getCidade().getNome());
                    cidade.setCidUf(criarUnidadeDto.getEndereco().getCidade().getUf());
                    cidade = cidadeRepository.save(cidade);
                }
                endereco.setCidade(cidade);
            }

            endereco = enderecoRepository.save(endereco);

            UnidadeEndereco unidadeEndereco = new UnidadeEndereco();
            unidadeEndereco.setUnidade(u);
            unidadeEndereco.setEndereco(endereco);
            unidadeEnderecoRepository.save(unidadeEndereco);
        }

        return u;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}