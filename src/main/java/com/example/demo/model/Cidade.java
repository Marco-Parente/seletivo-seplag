package com.example.demo.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cidade")
@Data
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cidId;

    private String cidNome;
    private String cidUf;

    @OneToMany(mappedBy = "cidade")
    private List<Endereco> enderecos;

    // Getters and Setters
}