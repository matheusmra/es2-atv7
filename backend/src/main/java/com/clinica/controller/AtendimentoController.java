package com.clinica.controller;

import com.clinica.dto.AtendimentoRequestDTO;
import com.clinica.dto.AtendimentoResponseDTO;
import com.clinica.service.AtendimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService service;

    @PostMapping
    public ResponseEntity<AtendimentoResponseDTO> criar(@Valid @RequestBody AtendimentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AtendimentoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
