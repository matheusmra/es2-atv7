package com.clinica.controller;

import com.clinica.dto.ExameLabRequestDTO;
import com.clinica.dto.ExameLabResponseDTO;
import com.clinica.service.ExameLabService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameLabController {

    private final ExameLabService service;

    @PostMapping
    public ResponseEntity<ExameLabResponseDTO> criar(@Valid @RequestBody ExameLabRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ExameLabResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameLabResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameLabResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ExameLabRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
