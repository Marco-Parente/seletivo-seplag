package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
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
import com.example.demo.model.Cidade;
import com.example.demo.model.FotoPessoa.FotoPessoaMapper;
import com.example.demo.model.Servidor.ObterServidorPorUnidadeDTO;
import com.example.demo.model.ServidorEfetivo.CriarServidorEfetivoDTO;
import com.example.demo.model.ServidorEfetivo.ObterServidorEfetivoDTO;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivo;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivoMapper;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivoModelAssembler;
import com.example.demo.model.PessoaEndereco;
import com.example.demo.model.Endereco.Endereco;
import com.example.demo.model.Endereco.ObterEnderecoDTO;
import com.example.demo.repository.CidadeRepository;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.PessoaEnderecoRepository;
import com.example.demo.repository.ServidorEfetivoRepository;
import com.example.demo.service.FotoService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/servidor-efetivo")
public class ServidorEfetivoController {

    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final ServidorEfetivoMapper servidorEfetivoMapper;
    private final ServidorEfetivoModelAssembler assembler;
    private final FotoService fotoService;
    private final FotoPessoaMapper mapper;
    private final EnderecoRepository enderecoRepository;
    private final PessoaEnderecoRepository pessoaEnderecoRepository;
    private final CidadeRepository cidadeRepository;

    public ServidorEfetivoController(ServidorEfetivoRepository seRepository,
            ServidorEfetivoMapper servidorEfetivoMapper, ServidorEfetivoModelAssembler assembler,
            FotoService photoService, FotoPessoaMapper mapper,
            EnderecoRepository enderecoRepository,
            PessoaEnderecoRepository pessoaEnderecoRepository,
            CidadeRepository cidadeRepository) {
        this.servidorEfetivoRepository = seRepository;
        this.servidorEfetivoMapper = servidorEfetivoMapper;
        this.assembler = assembler;
        this.fotoService = photoService;
        this.mapper = mapper;
        this.enderecoRepository = enderecoRepository;
        this.pessoaEnderecoRepository = pessoaEnderecoRepository;
        this.cidadeRepository = cidadeRepository;
    }

    @GetMapping
    @PageableAsQueryParam
    public CollectionModel<EntityModel<ObterServidorEfetivoDTO>> getAll(@Parameter(hidden = true) Pageable pageable) {
        List<EntityModel<ObterServidorEfetivoDTO>> servidores = servidorEfetivoRepository.findAll(pageable).stream()
                .map(se -> servidorEfetivoMapper.toObterServidorEfetivoDTO(se, fotoService))
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(servidores,
                linkTo(methodOn(ServidorEfetivoController.class).getAll(pageable)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObterServidorEfetivoDTO> getById(@PathVariable Integer id) {
        ServidorEfetivo servidor = servidorEfetivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(servidorEfetivoMapper.toObterServidorEfetivoDTO(servidor, fotoService));
    }

    @PostMapping
    public EntityModel<ObterServidorEfetivoDTO> create(@RequestBody CriarServidorEfetivoDTO criarServidorEfetivoDto) {
        var s = servidorEfetivoMapper.toServidorEfetivo(criarServidorEfetivoDto);
        var p = servidorEfetivoMapper.toPessoa(criarServidorEfetivoDto);
        s.setPessoa(p);
        
        s = servidorEfetivoRepository.save(s);

        if (criarServidorEfetivoDto.getPessoa() != null && 
            criarServidorEfetivoDto.getPessoa().getEndereco() != null) {
            
            var enderecoDto = criarServidorEfetivoDto.getPessoa().getEndereco();
            var endereco = new Endereco();
            endereco.setEndLogradouro(enderecoDto.getLogradouro());
            endereco.setEndTipoLogradouro(enderecoDto.getTipoLogradouro());
            endereco.setEndNumero(enderecoDto.getNumero());
            endereco.setEndBairro(enderecoDto.getBairro());

            if (enderecoDto.getCidade() != null) {
                Cidade cidade;
                if (enderecoDto.getCidade().getId() != null) {
                    cidade = cidadeRepository.findById(Integer.parseInt(enderecoDto.getCidade().getId()))
                            .orElseGet(() -> {
                                var novaCidade = new Cidade();
                                novaCidade.setCidNome(enderecoDto.getCidade().getNome());
                                novaCidade.setCidUf(enderecoDto.getCidade().getUf());
                                return cidadeRepository.save(novaCidade);
                            });
                } else {
                    cidade = new Cidade();
                    cidade.setCidNome(enderecoDto.getCidade().getNome());
                    cidade.setCidUf(enderecoDto.getCidade().getUf());
                    cidade = cidadeRepository.save(cidade);
                }
                endereco.setCidade(cidade);
            }
            
            endereco = enderecoRepository.save(endereco);

            var pessoaEndereco = new PessoaEndereco();
            pessoaEndereco.setPessoa(s.getPessoa());
            pessoaEndereco.setEndereco(endereco);
            pessoaEnderecoRepository.save(pessoaEndereco);
        }

        return assembler.toModel(servidorEfetivoMapper.toObterServidorEfetivoDTO(s, fotoService));
    }

    @PutMapping("/{id}")
    public EntityModel<ObterServidorEfetivoDTO> replace(@RequestBody CriarServidorEfetivoDTO criarServidorEfetivoDto,
            @PathVariable Integer id) {
        var updated = servidorEfetivoRepository.findById(id).map(se -> {
            var pessoa = se.getPessoa();

            pessoa.setPesNome(criarServidorEfetivoDto.getPessoa().getNome());
            pessoa.setPesMae(criarServidorEfetivoDto.getPessoa().getNomeMae());
            pessoa.setPesPai(criarServidorEfetivoDto.getPessoa().getNomePai());
            pessoa.setPesDataNascimento(criarServidorEfetivoDto.getPessoa().getDataNascimento());
            pessoa.setPesSexo(criarServidorEfetivoDto.getPessoa().getSexo());
            se.setPessoa(pessoa);

            se.setSeMatricula(criarServidorEfetivoDto.getMatricula());
            return servidorEfetivoRepository.save(se);
        }).orElseGet(() -> {
            var s = servidorEfetivoMapper.toServidorEfetivo(criarServidorEfetivoDto);
            var p = servidorEfetivoMapper.toPessoa(criarServidorEfetivoDto);
            s.setPessoa(p);
            return servidorEfetivoRepository.save(s);
        });

        return assembler.toModel(servidorEfetivoMapper.toObterServidorEfetivoDTO(updated, fotoService));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        servidorEfetivoRepository.deleteById(id);
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
            var servidores = servidorEfetivoRepository.findByPessoa_Lotacoes_Unidade_UnidId(unidadeId);
            var result = servidores.stream()
                .map(servidor -> {
                    var dto = servidorEfetivoMapper.toObterServidorPorUnidadeDTO(servidor);
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

    @GetMapping("/endereco-unidade")
    public ResponseEntity<List<ObterEnderecoDTO>> getEnderecoUnidadeByNome(@RequestParam String nome) {
        var servidores = servidorEfetivoRepository.findByPessoa_PesNomeContainingIgnoreCase(nome);
        List<ObterEnderecoDTO> enderecos = new ArrayList<>();
        
        for (var servidor : servidores) {
            var lotacaoAtual = servidor.getPessoa().getLotacoes().stream()
                .filter(l -> l.getLotDataRemocao() == null)
                .findFirst();
            
            if (lotacaoAtual.isPresent()) {
                var unidade = lotacaoAtual.get().getUnidade();
                var unidadeEndereco = unidade.getUnidadeEnderecos().stream()
                    .findFirst();
                
                if (unidadeEndereco.isPresent()) {
                    var endereco = unidadeEndereco.get().getEndereco();
                    var dto = new ObterEnderecoDTO();
                    dto.setTipoLogradouro(endereco.getEndTipoLogradouro());
                    dto.setLogradouro(endereco.getEndLogradouro());
                    dto.setNumero(endereco.getEndBairro());
                    dto.setBairro(endereco.getEndBairro());
                    dto.setUnidadeNome(unidade.getUnidNome());

                    if(endereco.getCidade() != null){
                        dto.setCidadeNome(endereco.getCidade().getCidNome());
                        dto.setCidadeUf(endereco.getCidade().getCidUf());
                    }
                    
                    enderecos.add(dto);
                }
            }
        }
        
        return ResponseEntity.ok(enderecos);
    }
}