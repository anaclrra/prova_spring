package com.example.centro_saude.controller.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.centro_saude.model.Paciente;
import com.example.centro_saude.repository.PacienteRepository;
import com.example.centro_saude.service.PacienteService;
import com.example.centro_saude.service.exception.CpfDuplicadoException;
import org.springframework.http.HttpStatus;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    
  @Autowired
  private PacienteRepository repository;

  @Autowired
  private PacienteService pacienteService;

  @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Paciente paciente,
            UriComponentsBuilder uriBuilder) {

                try {
                    Paciente pacienteSalvo = pacienteService.cadastrarPaciente(paciente);
                    var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(pacienteSalvo.getId()).toUri();
                    return ResponseEntity.created(uri).build();
                } catch (CpfDuplicadoException e) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
                }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> detalhar(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping
    public ResponseEntity<Page<Paciente>> listar(@PageableDefault(size = 30, sort = { "nome" }) Pageable paginacao) {
        var pacientes = repository.findAll(paginacao);
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        repository.delete(paciente);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody @Valid Paciente paciente ) {
        Paciente pacienteLocal = repository.findById(
                paciente.getId()).get();

        pacienteLocal.setNome(paciente.getNome());

        return ResponseEntity.ok(pacienteLocal);
    }


  
}