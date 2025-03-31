package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ServidorTemporario.ServidorTemporario;

public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporario, Integer> {
    List<ServidorTemporario> findByPessoa_Lotacoes_Unidade_UnidId(Integer unidId);
}