package com.clinica.dto;

import com.clinica.model.Atendimento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AtendimentoResponseDTO {

    private Long id;
    private LocalDate data;
    private LocalTime horario;
    private String problemaTexto;
    private String receitaSaude;
    private Long profissionalId;
    private String profissionalNome;
    private String profissionalCategoria;
    private List<ExameLabResponseDTO> exames;

    public AtendimentoResponseDTO(Atendimento entity) {
        this.id = entity.getId();
        this.data = entity.getData();
        this.horario = entity.getHorario();
        this.problemaTexto = entity.getProblemaTexto();
        this.receitaSaude = entity.getReceitaSaude();
        this.profissionalId = entity.getProfissional().getId();
        this.profissionalNome = entity.getProfissional().getNome();
        this.profissionalCategoria = entity.getProfissional().getCategoria().name();
        this.exames = entity.getExames().stream()
                .map(ExameLabResponseDTO::new)
                .toList();
    }
}
