package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Unidade.Unidade;

public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {
}