package com.example.demo.model.Endereco;

import java.util.List;

import com.example.demo.model.Cidade;
import com.example.demo.model.PessoaEndereco;
import com.example.demo.model.UnidadeEndereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "endereco")
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer endId;

    @Size(max = 50)
    @Column(length = 50)
    private String endTipoLogradouro;

    @Size(max = 200)
    @Column(length = 200)
    private String endLogradouro;

    @Size(max = 100)
    @Column(length = 100)
    private String endBairro;

    @Size(max = 100)
    @Column(length = 100)
    private String endNumero;

    @ManyToOne
    @JoinColumn(name = "cid_id")
    private Cidade cidade;

    @OneToMany(mappedBy = "endereco")
    private List<PessoaEndereco> pessoaEnderecos;

    @OneToMany(mappedBy = "endereco")
    private List<UnidadeEndereco> unidadeEnderecos;
    
}