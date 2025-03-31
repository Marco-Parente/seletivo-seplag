package com.example.demo.model;

import com.example.demo.model.Endereco.Endereco;
import com.example.demo.model.Unidade.Unidade;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unidade_endereco")
@Data
public class UnidadeEndereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "unid_id")
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "end_id")
    private Endereco endereco;
}