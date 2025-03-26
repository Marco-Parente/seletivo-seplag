package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "lotacao")
public class Lotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lotId;

    private Date lotDataLotacao;
    private Date lotDataRemocao;
    private String lotPortaria;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "unid_id")
    private Unidade unidade;

    // Getters and Setters
}