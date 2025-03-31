package com.example.demo.model.Endereco;

import lombok.Data;

@Data
public class ObterEnderecoDTO {
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidadeNome;
    private String cidadeUf;
    private String unidadeNome;
}