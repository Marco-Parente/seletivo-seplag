package com.example.demo.model.Lotacao;

import java.time.LocalDate;

import com.example.demo.model.Pessoa.Pessoa;
import com.example.demo.model.Unidade.Unidade;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lotacao")
@Data
public class Lotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lotId;

    private LocalDate lotDataLotacao;
    private LocalDate lotDataRemocao;
    private String lotPortaria;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "unid_id")
    private Unidade unidade;
}