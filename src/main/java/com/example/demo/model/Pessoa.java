package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pesId;

    private String pesNome;
    private Date pesDataNascimento;
    private String pesSexo;
    private String pesMae;
    private String pesPai;

    @OneToMany(mappedBy = "pessoa")
    private List<FotoPessoa> fotos;

    @OneToMany(mappedBy = "pessoa")
    private List<PessoaEndereco> enderecos;

    @OneToOne(mappedBy = "pessoa")
    private ServidorTemporario servidorTemporario;

    @OneToOne(mappedBy = "pessoa")
    private ServidorEfetivo servidorEfetivo;

    @OneToMany(mappedBy = "pessoa")
    private List<Lotacao> lotacoes;

    // Getters and Setters
}