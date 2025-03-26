package com.example.demo.repository;

import com.example.demo.model.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
}