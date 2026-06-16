package com.clinica.service;

import com.clinica.dto.ExameLabRequestDTO;
import com.clinica.dto.ExameLabResponseDTO;
import com.clinica.exception.ResourceNotFoundException;
import com.clinica.model.Atendimento;
import com.clinica.model.ExameLab;
import com.clinica.repository.AtendimentoRepository;
import com.clinica.repository.ExameLabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExameLabService {

    private final ExameLabRepository repository;
    private final AtendimentoRepository atendimentoRepository;

    public ExameLabResponseDTO criar(ExameLabRequestDTO dto) {
        Atendimento atendimento = atendimentoRepository.findById(dto.getAtendimentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado com id: " + dto.getAtendimentoId()));

        ExameLab entity = new ExameLab();
        entity.setDescricao(dto.getDescricao());
        entity.setAtendimento(atendimento);
        return new ExameLabResponseDTO(repository.save(entity));
    }

    public ExameLabResponseDTO buscarPorId(Long id) {
        return new ExameLabResponseDTO(findOrThrow(id));
    }

    public List<ExameLabResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ExameLabResponseDTO::new).toList();
    }

    public ExameLabResponseDTO atualizar(Long id, ExameLabRequestDTO dto) {
        ExameLab entity = findOrThrow(id);
        Atendimento atendimento = atendimentoRepository.findById(dto.getAtendimentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado com id: " + dto.getAtendimentoId()));

        entity.setDescricao(dto.getDescricao());
        entity.setAtendimento(atendimento);
        return new ExameLabResponseDTO(repository.save(entity));
    }

    public void excluir(Long id) {
        findOrThrow(id);
        repository.deleteById(id);
    }

    private ExameLab findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado com id: " + id));
    }
}
