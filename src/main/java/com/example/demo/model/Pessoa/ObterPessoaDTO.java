package com.example.demo.model.Pessoa;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.FotoPessoa.ObterFotoPessoaComLinkDTO;
import com.example.demo.util.Sexo;

import lombok.Data;

@Data
public class ObterPessoaDTO {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private Sexo sexo;
    private String nomeMae;
    private String nomePai;
    private List<ObterFotoPessoaComLinkDTO> fotos;
}
