package com.example.demo.model.FotoPessoa;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import com.example.demo.model.Pessoa.Pessoa;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "foto_pessoa")
@Data
public class FotoPessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpId;

    private Date fpData;
    @Size(max = 50)
    @Column(length = 50)
    private String fpBucket;

    @Size(max = 50)
    @Column(length = 50)
    private String fpHash;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;
}