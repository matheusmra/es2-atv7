package com.clinica.controller;

import com.clinica.dto.ProfissionalRequestDTO;
import com.clinica.dto.ProfissionalResponseDTO;
import com.clinica.model.CategoriaEnum;
import com.clinica.service.ProfissionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService service;

    @PostMapping
    public ResponseEntity<ProfissionalResponseDTO> criar(@Valid @RequestBody ProfissionalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) CategoriaEnum categoria) {
        if (nome != null) return ResponseEntity.ok(service.buscarPorNome(nome));
        if (categoria != null) return ResponseEntity.ok(service.buscarPorCategoria(categoria));
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProfissionalRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
