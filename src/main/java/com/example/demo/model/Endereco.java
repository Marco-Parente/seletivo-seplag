package com.example.demo.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "endereco")
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer endId;

    private String endTipoLogradouro;
    private String endLogradouro;
    private String endNumero;
    private String endBairro;

    @ManyToOne
    @JoinColumn(name = "cid_id")
    private Cidade cidade;

    @OneToMany(mappedBy = "endereco")
    private List<PessoaEndereco> pessoaEnderecos;

    @OneToMany(mappedBy = "endereco")
    private List<UnidadeEndereco> unidadeEnderecos;

    // Getters and Setters
}