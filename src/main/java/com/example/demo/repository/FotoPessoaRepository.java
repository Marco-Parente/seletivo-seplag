package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FotoPessoa.FotoPessoa;

public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Integer> {
    List<FotoPessoa> findByPessoa_PesId(Integer id);
}