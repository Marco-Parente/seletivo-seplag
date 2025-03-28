package com.example.demo.model.ServidorEfetivo;

import com.example.demo.model.Pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "servidor_efetivo")
@Data
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