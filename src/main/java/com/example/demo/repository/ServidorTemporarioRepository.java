package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ServidorTemporario.ServidorTemporario;

public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporario, Integer> {
}