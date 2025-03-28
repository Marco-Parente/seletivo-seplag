package com.example.demo.model;

import com.example.demo.model.Pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pessoa_endereco")
@Data
public class PessoaEndereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "end_id")
    private Endereco endereco;

    // Getters and Setters
}