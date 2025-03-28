package com.example.demo.model.Unidade;

import java.util.List;

import com.example.demo.model.UnidadeEndereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "unidade")
@Data
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unidId;

    @NotNull
    private String unidNome;

    @NotNull
    private String unidSigla;

    @OneToMany(mappedBy = "unidade")
    private List<UnidadeEndereco> unidadeEnderecos;
}