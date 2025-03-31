package com.example.demo.model.FotoPessoa;

import java.util.Date;

import lombok.Data;

@Data
public class ObterFotoPessoaComLinkDTO {

    private Integer id;
    private Date data;
    private String linkFoto;
}
