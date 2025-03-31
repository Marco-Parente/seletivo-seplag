package com.example.demo.model.ServidorEfetivo;

import java.time.LocalDate;
import java.time.Period;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.model.Pessoa.Pessoa;
import com.example.demo.model.Servidor.ObterServidorPorUnidadeDTO;
import com.example.demo.service.FotoService;

@Mapper(componentModel = "spring")
public interface ServidorEfetivoMapper {

    @Mapping(source = "matricula", target = "seMatricula")
    @Mapping(target = "pesId", ignore = true)
    @Mapping(target = "pessoa", ignore = true)
    ServidorEfetivo toServidorEfetivo(CriarServidorEfetivoDTO criarServidorEfetivoDTO);

    @Mapping(source = "pessoa.nome", target = "pesNome")
    @Mapping(source = "pessoa.nomePai", target = "pesPai")
    @Mapping(source = "pessoa.nomeMae", target = "pesMae")
    @Mapping(source = "pessoa.sexo", target = "pesSexo")
    @Mapping(source = "pessoa.dataNascimento", target = "pesDataNascimento")
    @Mapping(target = "pesId", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "lotacoes", ignore = true)
    @Mapping(target = "servidorEfetivo", ignore = true)
    @Mapping(target = "servidorTemporario", ignore = true)
    Pessoa toPessoa(CriarServidorEfetivoDTO criarServidorEfetivoDTO);

    @Mapping(source = "pessoa.pesNome", target = "nome")
    @Mapping(source = "pessoa.pesPai", target = "nomePai")
    @Mapping(source = "pessoa.pesMae", target = "nomeMae")
    @Mapping(source = "pessoa.pesSexo", target = "sexo")
    @Mapping(source = "pessoa.pesDataNascimento", target = "dataNascimento")
    @Mapping(source = "seMatricula", target = "matricula")
    @Mapping(source = "pesId", target = "id")
    @Mapping(target = "fotos", ignore = true)
    ObterServidorEfetivoDTO toObterServidorEfetivoDTO(ServidorEfetivo servidor, @Context FotoService photoService);

    @AfterMapping
    default void map(@MappingTarget ObterServidorEfetivoDTO target, ServidorEfetivo servidor,
            @Context FotoService photoService) {
                try {
                    var fotos = photoService.getPessoaPhotos(servidor.getPesId());
                    target.setFotos(fotos);
                } catch (Exception e) {
                    // TODO: LOG
                }
    }

    @Mapping(target = "nome", source = "pessoa.pesNome")
    @Mapping(target = "idade", expression = "java(calculateAge(servidor.getPessoa().getPesDataNascimento()))")
    @Mapping(target = "unidadeNome", expression = "java(getUnidadeName(servidor))")
    @Mapping(target = "fotos", ignore = true)
    ObterServidorPorUnidadeDTO toObterServidorPorUnidadeDTO(ServidorEfetivo servidor);

    default Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    default String getUnidadeName(ServidorEfetivo servidor) {
        return servidor.getPessoa().getLotacoes().stream()
            .findFirst()
            .map(l -> l.getUnidade().getUnidNome())
            .orElse(null);
    }
}