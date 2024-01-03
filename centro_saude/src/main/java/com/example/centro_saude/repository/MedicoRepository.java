package com.example.centro_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.centro_saude.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
}
