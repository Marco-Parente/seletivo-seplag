package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo {

    @Id
    private Integer pesId;

    private String seMatricula;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    // Getters and Setters
}