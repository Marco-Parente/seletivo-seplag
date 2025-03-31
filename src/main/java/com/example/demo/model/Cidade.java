package com.example.demo.model;

import java.util.List;

import com.example.demo.model.Endereco.Endereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "cidade")
@Data
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cidId;

    @Size(max = 200)
    @Column(length = 200)
    private String cidNome;

    @Size(max = 2)
    @Column(length = 2)
    private String cidUf;

    @OneToMany(mappedBy = "cidade")
    private List<Endereco> enderecos;
}