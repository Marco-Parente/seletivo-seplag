package com.example.demo.repository;

import com.example.demo.model.PessoaEndereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, Integer> {
}