package com.example.demo.model.ServidorTemporario;

import java.time.LocalDate;

import com.example.demo.model.Pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "servidor_temporario")
@Data
public class ServidorTemporario {

    @Id
    private Integer pesId;

    private LocalDate stDataAdmissao;
    private LocalDate stDataDemissao;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    // Getters and Setters
}