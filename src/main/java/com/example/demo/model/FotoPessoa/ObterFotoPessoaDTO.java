package com.example.demo.model.FotoPessoa;

import java.util.Date;

import lombok.Data;

@Data
public class ObterFotoPessoaDTO {
    private Integer id;
    private Date data;
    private String bucket;
    private String hash;
    private String pessoaId;
}
