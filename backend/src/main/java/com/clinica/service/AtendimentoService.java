package com.clinica.service;

import com.clinica.dto.AtendimentoRequestDTO;
import com.clinica.dto.AtendimentoResponseDTO;
import com.clinica.exception.ResourceNotFoundException;
import com.clinica.model.Atendimento;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.AtendimentoRepository;
import com.clinica.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository repository;
    private final ProfissionalRepository profissionalRepository;

    public AtendimentoResponseDTO criar(AtendimentoRequestDTO dto) {
        ProfissionalDeSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com id: " + dto.getProfissionalId()));

        Atendimento entity = new Atendimento();
        entity.setData(dto.getData());
        entity.setHorario(dto.getHorario());
        entity.setProblemaTexto(dto.getProblemaTexto());
        entity.setReceitaSaude(dto.getReceitaSaude());
        entity.setProfissional(profissional);
        return new AtendimentoResponseDTO(repository.save(entity));
    }

    public AtendimentoResponseDTO buscarPorId(Long id) {
        return new AtendimentoResponseDTO(findOrThrow(id));
    }

    public List<AtendimentoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(AtendimentoResponseDTO::new).toList();
    }

    public AtendimentoResponseDTO atualizar(Long id, AtendimentoRequestDTO dto) {
        Atendimento entity = findOrThrow(id);
        ProfissionalDeSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com id: " + dto.getProfissionalId()));

        entity.setData(dto.getData());
        entity.setHorario(dto.getHorario());
        entity.setProblemaTexto(dto.getProblemaTexto());
        entity.setReceitaSaude(dto.getReceitaSaude());
        entity.setProfissional(profissional);
        return new AtendimentoResponseDTO(repository.save(entity));
    }

    public void excluir(Long id) {
        findOrThrow(id);
        repository.deleteById(id);
    }

    private Atendimento findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado com id: " + id));
    }
}
