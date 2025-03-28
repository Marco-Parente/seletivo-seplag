package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.ServidorEfetivo.CriarServidorEfetivoDTO;
import com.example.demo.model.ServidorEfetivo.ObterServidorEfetivoDTO;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivo;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivoMapper;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivoModelAssembler;
import com.example.demo.repository.ServidorEfetivoRepository;

@RestController
@RequestMapping("/servidor-efetivo")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoRepository repository;

    @Autowired
    private ServidorEfetivoMapper servidorEfetivoMapper;

    @Autowired
    private ServidorEfetivoModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<ObterServidorEfetivoDTO>> getAll() {
        List<EntityModel<ObterServidorEfetivoDTO>> servidores = repository.findAll().stream()
                .map(servidorEfetivoMapper::toObterServidorEfetivoDTO)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(servidores, linkTo(methodOn(ServidorEfetivoController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObterServidorEfetivoDTO> getById(@PathVariable Integer id) {
        ServidorEfetivo servidor = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(servidorEfetivoMapper.toObterServidorEfetivoDTO(servidor));
    }

    @PostMapping
    public EntityModel<ObterServidorEfetivoDTO> create(@RequestBody CriarServidorEfetivoDTO criarServidorEfetivoDto) {
        var s = servidorEfetivoMapper.toServidorEfetivo(criarServidorEfetivoDto);
        var p = servidorEfetivoMapper.toPessoa(criarServidorEfetivoDto);
        s.setPessoa(p);

        return assembler.toModel(servidorEfetivoMapper.toObterServidorEfetivoDTO(repository.save(s)));
    }

    @PutMapping("/{id}")
    public EntityModel<ObterServidorEfetivoDTO> replace(@RequestBody CriarServidorEfetivoDTO criarServidorEfetivoDto,
            @PathVariable Integer id) {
        var updated = repository.findById(id).map(se -> {
            var pessoa = se.getPessoa();

            pessoa.setPesNome(criarServidorEfetivoDto.getNome());
            pessoa.setPesMae(criarServidorEfetivoDto.getNomeMae());
            pessoa.setPesPai(criarServidorEfetivoDto.getNomePai());
            pessoa.setPesDataNascimento(criarServidorEfetivoDto.getDataNascimento());
            pessoa.setPesSexo(criarServidorEfetivoDto.getSexo());
            se.setPessoa(pessoa);

            se.setSeMatricula(criarServidorEfetivoDto.getMatricula());
            return repository.save(se);
        }).orElseGet(() -> {
            var s = servidorEfetivoMapper.toServidorEfetivo(criarServidorEfetivoDto);
            var p = servidorEfetivoMapper.toPessoa(criarServidorEfetivoDto);
            s.setPessoa(p);
            return repository.save(s);
        });

        return assembler.toModel(servidorEfetivoMapper.toObterServidorEfetivoDTO(updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}