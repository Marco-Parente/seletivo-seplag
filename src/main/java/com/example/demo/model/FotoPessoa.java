package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import com.example.demo.model.Pessoa.Pessoa;

@Entity
@Table(name = "foto_pessoa")
@Data
public class FotoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpId;

    private Date fpData;
    private String fpBucket;
    private String fpHash;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    // Getters and Setters
}