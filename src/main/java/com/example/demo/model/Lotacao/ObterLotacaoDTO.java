package com.example.demo.model.Lotacao;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ObterLotacaoDTO {
    private Integer id;
    private String pessoaNome;
    private String unidadeNome;
    private LocalDate dataLotacao;
    private LocalDate dataRemocao;
    private String portaria;
}