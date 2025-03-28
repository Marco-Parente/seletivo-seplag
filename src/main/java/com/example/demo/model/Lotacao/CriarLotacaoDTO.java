package com.example.demo.model.Lotacao;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CriarLotacaoDTO {
    private Integer pessoaId;
    private Integer unidadeId;
    private LocalDate dataLotacao;
    private LocalDate dataRemocao;
    private String portaria;
}