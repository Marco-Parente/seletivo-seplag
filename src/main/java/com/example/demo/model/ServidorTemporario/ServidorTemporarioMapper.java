package com.example.demo.model.ServidorTemporario;

import java.time.LocalDate;
import java.time.Period;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.model.Servidor.ObterServidorPorUnidadeDTO;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivo;
import com.example.demo.service.FotoService;

@Mapper(componentModel = "spring")
public interface ServidorTemporarioMapper {

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
    @Mapping(target = "fotos", ignore = true)
    ObterServidorTemporarioDTO toObterServidorTemporarioDTO(ServidorTemporario servidor, @Context FotoService fotoService);

    
    @AfterMapping
    default void map(@MappingTarget ObterServidorTemporarioDTO target, ServidorEfetivo servidor,
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
    ObterServidorPorUnidadeDTO toObterServidorPorUnidadeDTO(ServidorTemporario servidor);

    default Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    default String getUnidadeName(ServidorTemporario servidor) {
        return servidor.getPessoa().getLotacoes().stream()
            .filter(l -> l.getLotDataRemocao() == null)
            .findFirst()
            .map(l -> l.getUnidade().getUnidNome())
            .orElse(null);
    }
}