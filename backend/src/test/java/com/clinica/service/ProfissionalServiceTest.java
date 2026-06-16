package com.clinica.service;

import com.clinica.dto.ProfissionalRequestDTO;
import com.clinica.dto.ProfissionalResponseDTO;
import com.clinica.exception.ResourceNotFoundException;
import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfissionalServiceTest {

    @Mock
    private ProfissionalRepository repository;

    @InjectMocks
    private ProfissionalService service;

    @Test
    void deveSalvarProfissionalComDadosValidos() {
        ProfissionalRequestDTO dto = new ProfissionalRequestDTO();
        dto.setNome("Dr. João");
        dto.setTelefone("11999999999");
        dto.setEndereco("Rua A");
        dto.setCategoria(CategoriaEnum.MEDICO);

        ProfissionalDeSaude saved = buildProfissional(1L, "Dr. João", CategoriaEnum.MEDICO);
        when(repository.save(any())).thenReturn(saved);

        ProfissionalResponseDTO result = service.criar(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNome()).isEqualTo("Dr. João");
        verify(repository).save(any());
    }

    @Test
    void deveLancarExcecaoAoBuscarProfissionalInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deveRetornarListaPorCategoria() {
        ProfissionalDeSaude p = buildProfissional(1L, "Ana", CategoriaEnum.PSICOLOGO);
        when(repository.findByCategoria(CategoriaEnum.PSICOLOGO)).thenReturn(List.of(p));

        List<ProfissionalResponseDTO> result = service.buscarPorCategoria(CategoriaEnum.PSICOLOGO);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoria()).isEqualTo(CategoriaEnum.PSICOLOGO);
    }

    @Test
    void deveLancarExcecaoAoExcluirProfissionalInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.excluir(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private ProfissionalDeSaude buildProfissional(Long id, String nome, CategoriaEnum cat) {
        ProfissionalDeSaude p = new ProfissionalDeSaude();
        p.setId(id);
        p.setNome(nome);
        p.setTelefone("11999999999");
        p.setEndereco("Rua A");
        p.setCategoria(cat);
        return p;
    }
}
