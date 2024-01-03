package com.example.centro_saude.controller.consulta;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.centro_saude.model.Consulta;
import com.example.centro_saude.repository.ConsultaRepository;
import com.example.centro_saude.service.ConsultaService;

import org.springframework.web.util.UriComponentsBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> criarConsulta(@RequestBody @Valid Consulta consulta,
                                                UriComponentsBuilder uriBuilder) {
        Consulta consultaSalva = consultaService.salvarConsulta(consulta);
        var uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consultaSalva.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarConsulta(@PathVariable Long id) {
        return consultaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Consulta>> listarConsultas(@PageableDefault(size = 10, sort = "dataConsulta") Pageable paginacao) {
        var consultas = consultaRepository.findAll(paginacao);
        return ResponseEntity.ok(consultas);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Consulta> atualizarConsulta(@PathVariable Long id, @RequestBody @Valid Consulta dadosConsulta) {
        return consultaRepository.findById(id).map(consultaExistente -> {
            consultaExistente.setMedico(dadosConsulta.getMedico());
            consultaExistente.setPaciente(dadosConsulta.getPaciente());
            consultaExistente.setDataConsulta(dadosConsulta.getDataConsulta());
            consultaExistente.setDiagnostico(dadosConsulta.getDiagnostico());
            consultaExistente.setPrescricao(dadosConsulta.getPrescricao());
            consultaService.salvarConsulta(consultaExistente);
            return ResponseEntity.ok(consultaExistente);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> removerConsulta(@PathVariable Long id) {
        return consultaRepository.findById(id).map(consulta -> {
            consultaRepository.delete(consulta);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
