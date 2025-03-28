package com.example.demo.model.Unidade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnidadeMapper {

    @Mapping(source = "nome", target = "unidNome")
    @Mapping(source = "sigla", target = "unidSigla")
    @Mapping(target = "unidId", ignore = true)
    @Mapping(target = "unidadeEnderecos", ignore = true)
    Unidade toUnidade(CriarUnidadeDTO criarUnidadeDTO);

    @Mapping(source = "unidNome", target = "nome")
    @Mapping(source = "unidSigla", target = "sigla")
    @Mapping(source = "unidId", target = "id")
    ObterUnidadeDTO toObterUnidadeDTO(Unidade unidade);

}
