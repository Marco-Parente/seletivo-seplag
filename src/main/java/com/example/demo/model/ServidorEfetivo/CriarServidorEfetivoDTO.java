package com.example.demo.model.ServidorEfetivo;

import com.example.demo.model.Pessoa.CriarPessoaDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarServidorEfetivoDTO extends CriarPessoaDTO {
    private String matricula;
}