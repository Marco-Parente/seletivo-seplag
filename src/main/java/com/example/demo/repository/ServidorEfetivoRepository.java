package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ServidorEfetivo.ServidorEfetivo;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Integer> {
    List<ServidorEfetivo> findByPessoa_Lotacoes_Unidade_UnidId(Integer unidId);
    List<ServidorEfetivo> findByPessoa_PesNomeContainingIgnoreCase(String nome);
}