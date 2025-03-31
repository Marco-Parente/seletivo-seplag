package com.example.demo.model.Pessoa;

import java.time.LocalDate;

import com.example.demo.model.Endereco.CriarEnderecoDTO;
import com.example.demo.util.Sexo;

import lombok.Data;

@Data
public class CriarPessoaDTO {
    private String nome;
    private LocalDate dataNascimento;
    private Sexo sexo;
    private String nomeMae;
    private String nomePai;
    private CriarEnderecoDTO endereco;
}
