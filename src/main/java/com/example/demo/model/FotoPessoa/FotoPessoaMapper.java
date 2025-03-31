package com.example.demo.model.FotoPessoa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FotoPessoaMapper {
    @Mapping(source = "fpId", target = "id")
    @Mapping(source = "fpData", target = "data")
    @Mapping(source = "pessoa.pesId", target = "pessoaId")
    @Mapping(source = "fpBucket", target = "bucket")
    @Mapping(source = "fpHash", target = "hash")
    ObterFotoPessoaDTO toDTO(FotoPessoa entity);

    @Mapping(target = "linkFoto", source = "presignedUrl")
    @Mapping(source = "entity.fpId", target = "id")
    @Mapping(source = "entity.fpData", target = "data")
    ObterFotoPessoaComLinkDTO toDTO(FotoPessoa entity, String presignedUrl);
}