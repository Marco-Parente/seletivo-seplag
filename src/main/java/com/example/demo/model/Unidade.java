package com.example.demo.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "unidade")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unidId;

    private String unidNome;
    private String unidSigla;

    @OneToMany(mappedBy = "unidade")
    private List<UnidadeEndereco> unidadeEnderecos;

    // Getters and Setters
}