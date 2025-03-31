package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.FotoPessoa.FotoPessoaMapper;
import com.example.demo.model.Servidor.ObterServidorPorUnidadeDTO;
import com.example.demo.model.ServidorTemporario.CriarServidorTemporarioDTO;
import com.example.demo.model.ServidorTemporario.ObterServidorTemporarioDTO;
import com.example.demo.model.ServidorTemporario.ServidorTemporario;
import com.example.demo.model.ServidorTemporario.ServidorTemporarioMapper;
import com.example.demo.model.ServidorTemporario.ServidorTemporarioModelAssembler;
import com.example.demo.model.PessoaEndereco;
import com.example.demo.model.Endereco.Endereco;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.PessoaEnderecoRepository;
import com.example.demo.repository.ServidorTemporarioRepository;
import com.example.demo.service.FotoService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/servidor-temporario")
public class ServidorTemporarioController {

    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final ServidorTemporarioMapper servidorTemporarioMapper;
    private final ServidorTemporarioModelAssembler assembler;
    private final FotoService fotoService;
    private final FotoPessoaMapper mapper;
    private final EnderecoRepository enderecoRepository;
    private final PessoaEnderecoRepository pessoaEnderecoRepository;

    public ServidorTemporarioController(ServidorTemporarioRepository repository,
            ServidorTemporarioMapper servidorTemporarioMapper, ServidorTemporarioModelAssembler assembler,
            FotoService fotoService, FotoPessoaMapper mapper,
            EnderecoRepository enderecoRepository,
            PessoaEnderecoRepository pessoaEnderecoRepository) {
        this.servidorTemporarioRepository = repository;
        this.servidorTemporarioMapper = servidorTemporarioMapper;
        this.assembler = assembler;
        this.fotoService = fotoService;
        this.mapper = mapper;
        this.enderecoRepository = enderecoRepository;
        this.pessoaEnderecoRepository = pessoaEnderecoRepository;
    }

    @GetMapping
    @PageableAsQueryParam
    public CollectionModel<EntityModel<ObterServidorTemporarioDTO>> getAll(@Parameter(hidden = true) Pageable pageable) {
        List<EntityModel<ObterServidorTemporarioDTO>> servidores = servidorTemporarioRepository.findAll(pageable).stream()
                .map(st -> servidorTemporarioMapper.toObterServidorTemporarioDTO(st, fotoService))
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(servidores, linkTo(methodOn(ServidorTemporarioController.class).getAll(pageable)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObterServidorTemporarioDTO> getById(@PathVariable Integer id) {
        ServidorTemporario servidor = servidorTemporarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(servidorTemporarioMapper.toObterServidorTemporarioDTO(servidor, fotoService));
    }

    @PostMapping
    public EntityModel<ObterServidorTemporarioDTO> create(@RequestBody CriarServidorTemporarioDTO criarServidorTemporarioDto) {
        var s = servidorTemporarioMapper.toServidorTemporario(criarServidorTemporarioDto);
        s = servidorTemporarioRepository.save(s);

        if (criarServidorTemporarioDto.getPessoa() != null && 
            criarServidorTemporarioDto.getPessoa().getEndereco() != null) {
            
            var enderecoDto = criarServidorTemporarioDto.getPessoa().getEndereco();
            Endereco endereco = new Endereco();
            endereco.setEndLogradouro(enderecoDto.getLogradouro());
            endereco.setEndTipoLogradouro(enderecoDto.getTipoLogradouro());
            endereco.setEndNumero(enderecoDto.getNumero());
            endereco.setEndBairro(enderecoDto.getBairro());
            endereco = enderecoRepository.save(endereco);

            PessoaEndereco pessoaEndereco = new PessoaEndereco();
            pessoaEndereco.setPessoa(s.getPessoa());
            pessoaEndereco.setEndereco(endereco);
            pessoaEnderecoRepository.save(pessoaEndereco);
        }

        return assembler.toModel(servidorTemporarioMapper.toObterServidorTemporarioDTO(s, fotoService));
    }

    @PutMapping("/{id}")
    public EntityModel<ObterServidorTemporarioDTO> replace(@RequestBody CriarServidorTemporarioDTO criarServidorTemporarioDto,
            @PathVariable Integer id) {
        var updated = servidorTemporarioRepository.findById(id).map(st -> {
            var pessoa = st.getPessoa();

            pessoa.setPesNome(criarServidorTemporarioDto.getPessoa().getNome());
            pessoa.setPesMae(criarServidorTemporarioDto.getPessoa().getNomeMae());
            pessoa.setPesPai(criarServidorTemporarioDto.getPessoa().getNomePai());
            pessoa.setPesDataNascimento(criarServidorTemporarioDto.getPessoa().getDataNascimento());
            pessoa.setPesSexo(criarServidorTemporarioDto.getPessoa().getSexo());
            st.setPessoa(pessoa);

            st.setStDataAdmissao(criarServidorTemporarioDto.getStDataAdmissao());
            st.setStDataDemissao(criarServidorTemporarioDto.getStDataDemissao());

            return servidorTemporarioRepository.save(st);
        }).orElseGet(() -> {
            var s = servidorTemporarioMapper.toServidorTemporario(criarServidorTemporarioDto);
            return servidorTemporarioRepository.save(s);
        });

        return assembler.toModel(servidorTemporarioMapper.toObterServidorTemporarioDTO(updated, fotoService));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        servidorTemporarioRepository.deleteById(id);
    }

    @PostMapping(value = "/{id}/fotos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFoto(@PathVariable Integer id,
            @RequestParam("foto") MultipartFile foto) {
        try {
            var fotoPessoa = fotoService.savePhoto(foto, id);
            return ResponseEntity.ok(mapper.toDTO(fotoPessoa));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/fotos")
    public ResponseEntity<?> getPhotos(@PathVariable Integer id) {
        try {
            var photos = fotoService.getPessoaPhotos(id);
            return ResponseEntity.ok()
                    .body(photos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving photo: " + e.getMessage());
        }
    }

    @DeleteMapping("/fotos/{photoId}")
    public ResponseEntity<?> deletePhoto(@PathVariable Integer photoId) {
        fotoService.deletePhoto(photoId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<ObterServidorPorUnidadeDTO>> getByUnidadeId(@PathVariable Integer unidadeId) {
        try {
            var servidores = servidorTemporarioRepository.findByPessoa_Lotacoes_Unidade_UnidId(unidadeId);
            var result = servidores.stream()
                .map(servidor -> {
                    var dto = servidorTemporarioMapper.toObterServidorPorUnidadeDTO(servidor);
                    try {
                        dto.setFotos(fotoService.getPessoaPhotos(servidor.getPesId()));
                    } catch (Exception e) {
                        // Log error and continue without photos
                        dto.setFotos(Collections.emptyList());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}