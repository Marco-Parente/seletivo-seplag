package com.example.demo.model.ServidorTemporario;

import java.time.LocalDate;

import com.example.demo.model.Pessoa.ObterPessoaDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObterServidorTemporarioDTO extends ObterPessoaDTO {
    private Integer id;
    
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
}