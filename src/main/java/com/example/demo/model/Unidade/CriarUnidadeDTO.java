package com.example.demo.model.Unidade;

import com.example.demo.model.Endereco.CriarEnderecoDTO;
import lombok.Data;

@Data
public class CriarUnidadeDTO {
    private String nome;
    private String sigla;
    private CriarEnderecoDTO endereco;
}
