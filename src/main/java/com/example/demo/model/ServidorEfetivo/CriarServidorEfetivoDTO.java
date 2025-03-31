package com.example.demo.model.ServidorEfetivo;

import com.example.demo.model.Pessoa.CriarPessoaDTO;

import lombok.Data;

@Data
public class CriarServidorEfetivoDTO {
    private String matricula;
    private CriarPessoaDTO pessoa;
}