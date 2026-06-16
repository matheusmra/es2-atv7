package com.clinica.service;

import com.clinica.dto.AtendimentoRequestDTO;
import com.clinica.dto.AtendimentoResponseDTO;
import com.clinica.exception.ResourceNotFoundException;
import com.clinica.model.Atendimento;
import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.AtendimentoRepository;
import com.clinica.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtendimentoServiceTest {

    @Mock
    private AtendimentoRepository repository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private AtendimentoService service;

    @Test
    void deveCriarAtendimentoVinculadoAProfissionalExistente() {
        ProfissionalDeSaude prof = buildProfissional(1L, CategoriaEnum.MEDICO);
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(prof));

        Atendimento saved = buildAtendimento(1L, prof);
        when(repository.save(any())).thenReturn(saved);

        AtendimentoRequestDTO dto = buildRequest(1L);

        AtendimentoResponseDTO result = service.criar(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProfissionalId()).isEqualTo(1L);
        verify(repository).save(any());
    }

    @Test
    void deveLancarExcecaoAoVincularProfissionalInexistente() {
        when(profissionalRepository.findById(99L)).thenReturn(Optional.empty());

        AtendimentoRequestDTO dto = buildRequest(99L);

        assertThatThrownBy(() -> service.criar(dto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deveValidarReceitaSaudeConformeCategoriaDoProfissional() {
        ProfissionalDeSaude prof = buildProfissional(1L, CategoriaEnum.FISIOTERAPEUTA);
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(prof));

        Atendimento saved = buildAtendimento(1L, prof);
        saved.setReceitaSaude("Caminhada 30min");
        when(repository.save(any())).thenReturn(saved);

        AtendimentoRequestDTO dto = buildRequest(1L);
        dto.setReceitaSaude("Caminhada 30min");

        AtendimentoResponseDTO result = service.criar(dto);

        assertThat(result.getReceitaSaude()).isEqualTo("Caminhada 30min");
    }

    private AtendimentoRequestDTO buildRequest(Long profId) {
        AtendimentoRequestDTO dto = new AtendimentoRequestDTO();
        dto.setData(LocalDate.of(2025, 1, 10));
        dto.setHorario(LocalTime.of(14, 0));
        dto.setProblemaTexto("Dor nas costas");
        dto.setReceitaSaude("Ibuprofeno");
        dto.setProfissionalId(profId);
        return dto;
    }

    private ProfissionalDeSaude buildProfissional(Long id, CategoriaEnum cat) {
        ProfissionalDeSaude p = new ProfissionalDeSaude();
        p.setId(id);
        p.setNome("Dr. Teste");
        p.setTelefone("11999999999");
        p.setEndereco("Rua A");
        p.setCategoria(cat);
        return p;
    }

    private Atendimento buildAtendimento(Long id, ProfissionalDeSaude prof) {
        Atendimento a = new Atendimento();
        a.setId(id);
        a.setData(LocalDate.of(2025, 1, 10));
        a.setHorario(LocalTime.of(14, 0));
        a.setProblemaTexto("Dor nas costas");
        a.setReceitaSaude("Ibuprofeno");
        a.setProfissional(prof);
        a.setExames(new ArrayList<>());
        return a;
    }
}
