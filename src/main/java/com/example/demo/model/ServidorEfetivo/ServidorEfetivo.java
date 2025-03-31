package com.example.demo.model.ServidorEfetivo;

import com.example.demo.model.Pessoa.Pessoa;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "servidor_efetivo")
@Data
public class ServidorEfetivo {

    @Id
    private Integer pesId;

    @Size(max = 20)
    @Column(length = 20)
    private String seMatricula;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;
}