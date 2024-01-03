package com.example.centro_saude.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.centro_saude.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
}
