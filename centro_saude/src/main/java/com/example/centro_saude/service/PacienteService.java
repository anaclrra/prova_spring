package com.example.centro_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.centro_saude.model.Paciente;
import com.example.centro_saude.repository.PacienteRepository;
import com.example.centro_saude.service.exception.CpfDuplicadoException;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente cadastrarPaciente(Paciente paciente) {
        if (pacienteRepository.findByCpf(paciente.getCpf()).isPresent()) {
    
            throw new CpfDuplicadoException(paciente.getCpf());
        }
        return pacienteRepository.save(paciente);

    }
}
