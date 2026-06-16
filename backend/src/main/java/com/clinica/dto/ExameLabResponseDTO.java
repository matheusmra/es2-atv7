package com.clinica.dto;

import com.clinica.model.ExameLab;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExameLabResponseDTO {

    private Long id;
    private String descricao;
    private Long atendimentoId;

    public ExameLabResponseDTO(ExameLab entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.atendimentoId = entity.getAtendimento().getId();
    }
}
