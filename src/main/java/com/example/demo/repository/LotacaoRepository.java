package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Lotacao.Lotacao;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
    List<Lotacao> findByUnidade_UnidIdAndPessoa_PesId(Integer unidId, Integer pesId);
}