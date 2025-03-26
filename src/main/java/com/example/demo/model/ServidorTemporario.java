package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario {

    @Id
    private Integer pesId;

    private Date stDataAdmissao;
    private Date stDataDemissao;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    // Getters and Setters
}