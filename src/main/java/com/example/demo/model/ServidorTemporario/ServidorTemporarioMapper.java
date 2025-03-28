package com.example.demo.model.ServidorTemporario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServidorTemporarioMapper {

    @Mapping(source = "nome", target = "pessoa.pesNome")
    @Mapping(target = "pesId", ignore = true)
    @Mapping(target = "pessoa.pesSexo", ignore = true)
    @Mapping(target = "pessoa.pesDataNascimento", ignore = true)
    @Mapping(target = "pessoa.pesMae", ignore = true)
    @Mapping(target = "pessoa.pesPai", ignore = true)
    ServidorTemporario toServidorTemporario(CriarServidorTemporarioDTO criarServidorTemporarioDTO);

    @Mapping(source = "pessoa.pesNome", target = "nome")
    @Mapping(source = "pessoa.pesPai", target = "nomePai")
    @Mapping(source = "pessoa.pesMae", target = "nomeMae")
    @Mapping(source = "pessoa.pesSexo", target = "sexo")
    @Mapping(source = "pessoa.pesDataNascimento", target = "dataNascimento")
    @Mapping(source = "pesId", target = "id")
    @Mapping(source = "stDataAdmissao", target = "dataAdmissao")
    @Mapping(source = "stDataDemissao", target = "dataDemissao")
    ObterServidorTemporarioDTO toObterServidorTemporarioDTO(ServidorTemporario servidor);
}