package com.example.demo.model.Pessoa;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.PessoaEndereco;
import com.example.demo.model.FotoPessoa.FotoPessoa;
import com.example.demo.model.Lotacao.Lotacao;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivo;
import com.example.demo.model.ServidorTemporario.ServidorTemporario;
import com.example.demo.util.Sexo;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pessoa")
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pesId;

    @Size(max = 200)
    @Column(length = 200)
    private String pesNome;

    private LocalDate pesDataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo pesSexo;

    @Size(max = 200)
    @Column(length = 200)
    private String pesMae;

    @Size(max = 200)
    @Column(length = 200)
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

    public List<Lotacao> getLotacoes() {
        return lotacoes;
    }
}