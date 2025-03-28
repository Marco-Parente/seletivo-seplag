package com.example.demo.model.ServidorEfetivo;

import com.example.demo.model.Pessoa.ObterPessoaDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObterServidorEfetivoDTO extends ObterPessoaDTO {
    private String matricula;
}