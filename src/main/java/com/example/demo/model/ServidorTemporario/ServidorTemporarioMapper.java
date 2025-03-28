package com.example.demo.model.ServidorTemporario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Pessoa.Pessoa;

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
    @Mapping(source = "pesId", target = "id")
    ObterServidorTemporarioDTO toObterServidorTemporarioDTO(ServidorTemporario servidor);
}