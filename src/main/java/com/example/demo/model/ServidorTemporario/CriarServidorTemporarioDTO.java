package com.example.demo.model.ServidorTemporario;

import java.time.LocalDate;

import com.example.demo.model.Pessoa.CriarPessoaDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarServidorTemporarioDTO extends CriarPessoaDTO {
    private String nome;
    
    private LocalDate stDataAdmissao;
    private LocalDate stDataDemissao;
}