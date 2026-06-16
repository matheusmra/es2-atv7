package com.clinica.service;

import com.clinica.dto.ProfissionalRequestDTO;
import com.clinica.dto.ProfissionalResponseDTO;
import com.clinica.exception.ResourceNotFoundException;
import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository repository;

    public ProfissionalResponseDTO criar(ProfissionalRequestDTO dto) {
        ProfissionalDeSaude entity = new ProfissionalDeSaude();
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(dto.getEndereco());
        entity.setCategoria(dto.getCategoria());
        return new ProfissionalResponseDTO(repository.save(entity));
    }

    public ProfissionalResponseDTO buscarPorId(Long id) {
        return new ProfissionalResponseDTO(findOrThrow(id));
    }

    public List<ProfissionalResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(ProfissionalResponseDTO::new).toList();
    }

    public List<ProfissionalResponseDTO> buscarPorCategoria(CategoriaEnum categoria) {
        return repository.findByCategoria(categoria).stream()
                .map(ProfissionalResponseDTO::new).toList();
    }

    public List<ProfissionalResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ProfissionalResponseDTO::new).toList();
    }

    public ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO dto) {
        ProfissionalDeSaude entity = findOrThrow(id);
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(dto.getEndereco());
        entity.setCategoria(dto.getCategoria());
        return new ProfissionalResponseDTO(repository.save(entity));
    }

    public void excluir(Long id) {
        findOrThrow(id);
        repository.deleteById(id);
    }

    private ProfissionalDeSaude findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com id: " + id));
    }
}
