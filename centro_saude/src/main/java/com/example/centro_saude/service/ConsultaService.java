package com.example.centro_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.centro_saude.model.Consulta;
import com.example.centro_saude.repository.ConsultaRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta salvarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

}