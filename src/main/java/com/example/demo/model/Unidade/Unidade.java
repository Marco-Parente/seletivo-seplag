package com.example.demo.model.Unidade;

import java.util.List;

import com.example.demo.model.UnidadeEndereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "unidade")
@Data
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unidId;

    @NotNull
    @Size(max = 200)
    @Column(length = 200)
    private String unidNome;

    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String unidSigla;

    @OneToMany(mappedBy = "unidade")
    private List<UnidadeEndereco> unidadeEnderecos;
}