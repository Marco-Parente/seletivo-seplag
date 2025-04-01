package com.example.demo.model.Endereco;

import lombok.Data;

@Data
public class CriarEnderecoDTO {
    private String logradouro;
    private String tipoLogradouro;
    private String numero;
    private String bairro;
    private CriarCidadeDTO cidade;
}
