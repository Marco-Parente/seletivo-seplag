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
import com.example.demo.model.ServidorTemporario.CriarServidorTemporarioDTO;
import com.example.demo.model.ServidorTemporario.ObterServidorTemporarioDTO;
import com.example.demo.model.ServidorTemporario.ServidorTemporario;
import com.example.demo.model.ServidorTemporario.ServidorTemporarioMapper;
import com.example.demo.model.ServidorTemporario.ServidorTemporarioModelAssembler;
import com.example.demo.repository.ServidorTemporarioRepository;

@RestController
@RequestMapping("/servidor-temporario")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioRepository repository;

    @Autowired
    private ServidorTemporarioMapper servidorTemporarioMapper;

    @Autowired
    private ServidorTemporarioModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<ObterServidorTemporarioDTO>> getAll() {
        List<EntityModel<ObterServidorTemporarioDTO>> servidores = repository.findAll().stream()
                .map(servidorTemporarioMapper::toObterServidorTemporarioDTO)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(servidores, linkTo(methodOn(ServidorTemporarioController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObterServidorTemporarioDTO> getById(@PathVariable Integer id) {
        ServidorTemporario servidor = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(servidorTemporarioMapper.toObterServidorTemporarioDTO(servidor));
    }

    @PostMapping
    public EntityModel<ObterServidorTemporarioDTO> create(@RequestBody CriarServidorTemporarioDTO criarServidorTemporarioDto) {
        var s = servidorTemporarioMapper.toServidorTemporario(criarServidorTemporarioDto);
        return assembler.toModel(servidorTemporarioMapper.toObterServidorTemporarioDTO(repository.save(s)));
    }

    @PutMapping("/{id}")
    public EntityModel<ObterServidorTemporarioDTO> replace(@RequestBody CriarServidorTemporarioDTO criarStDto,
            @PathVariable Integer id) {
        var updated = repository.findById(id).map(st -> {
            var pessoa = st.getPessoa();

            pessoa.setPesNome(criarStDto.getNome());
            pessoa.setPesMae(criarStDto.getNomeMae());
            pessoa.setPesPai(criarStDto.getNomePai());
            pessoa.setPesDataNascimento(criarStDto.getDataNascimento());
            pessoa.setPesSexo(criarStDto.getSexo());
            st.setPessoa(pessoa);

            st.setStDataAdmissao(criarStDto.getStDataAdmissao());
            st.setStDataDemissao(criarStDto.getStDataDemissao());

            return repository.save(st);
        }).orElseGet(() -> {
            var s = servidorTemporarioMapper.toServidorTemporario(criarStDto);
            return repository.save(s);
        });

        return assembler.toModel(servidorTemporarioMapper.toObterServidorTemporarioDTO(updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}