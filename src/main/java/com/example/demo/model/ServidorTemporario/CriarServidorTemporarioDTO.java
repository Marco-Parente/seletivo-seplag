package com.example.demo.model.ServidorTemporario;

import java.time.LocalDate;

import com.example.demo.model.Pessoa.CriarPessoaDTO;

import lombok.Data;

@Data
public class CriarServidorTemporarioDTO {
    private LocalDate stDataAdmissao;
    private LocalDate stDataDemissao;

    private CriarPessoaDTO pessoa;
}