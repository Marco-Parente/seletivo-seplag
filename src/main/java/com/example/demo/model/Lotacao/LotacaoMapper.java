package com.example.demo.model.Lotacao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LotacaoMapper {

    @Mapping(source = "pessoaId", target = "pessoa.pesId")
    @Mapping(source = "unidadeId", target = "unidade.unidId")
    @Mapping(source = "dataLotacao", target = "lotDataLotacao")
    @Mapping(source = "dataRemocao", target = "lotDataRemocao")
    @Mapping(source = "portaria", target = "lotPortaria")
    @Mapping(target = "lotId", ignore = true)
    Lotacao toLotacao(CriarLotacaoDTO criarLotacaoDTO);

    @Mapping(source = "pessoa.pesNome", target = "pessoaNome")
    @Mapping(source = "unidade.unidNome", target = "unidadeNome")
    @Mapping(source = "lotDataLotacao", target = "dataLotacao")
    @Mapping(source = "lotDataRemocao", target = "dataRemocao")
    @Mapping(source = "lotPortaria", target = "portaria")
    @Mapping(source = "lotId", target = "id")
    ObterLotacaoDTO toObterLotacaoDTO(Lotacao lotacao);
}