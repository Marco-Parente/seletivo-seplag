package com.example.demo.model.ServidorEfetivo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Pessoa.Pessoa;

@Mapper(componentModel = "spring")
public interface ServidorEfetivoMapper {

    @Mapping(source = "matricula", target = "seMatricula")
    @Mapping(target = "pesId", ignore = true)
    @Mapping(target = "pessoa", ignore = true)
    ServidorEfetivo toServidorEfetivo(CriarServidorEfetivoDTO criarServidorEfetivoDTO);

    @Mapping(source = "nome", target = "pesNome")
    @Mapping(source = "nomePai", target = "pesPai")
    @Mapping(source = "nomeMae", target = "pesMae")
    @Mapping(source = "sexo", target = "pesSexo")
    @Mapping(source = "dataNascimento", target = "pesDataNascimento")
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
    ObterServidorEfetivoDTO toObterServidorEfetivoDTO(ServidorEfetivo servidor);
}