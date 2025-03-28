package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Lotacao.Lotacao;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
}