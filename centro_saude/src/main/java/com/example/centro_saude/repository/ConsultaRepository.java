package com.example.centro_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.centro_saude.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
}
