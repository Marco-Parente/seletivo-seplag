package com.example.demo.model.Servidor;

import java.util.List;
import com.example.demo.model.FotoPessoa.ObterFotoPessoaComLinkDTO;
import lombok.Data;

@Data
public class ObterServidorPorUnidadeDTO {
    private String nome;
    private Integer idade;
    private String unidadeNome;
    private List<ObterFotoPessoaComLinkDTO> fotos;
}