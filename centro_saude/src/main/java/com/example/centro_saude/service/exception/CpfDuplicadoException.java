package com.example.centro_saude.service.exception;


public class CpfDuplicadoException extends RuntimeException {

    public CpfDuplicadoException(String cpf) {
        super("Um paciente com o CPF '" + cpf + "' já está cadastrado.");
    }

}

 
