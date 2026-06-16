package com.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExameLabRequestDTO {

    @NotBlank(message = "Descrição do exame é obrigatória")
    private String descricao;

    @NotNull(message = "Atendimento é obrigatório")
    private Long atendimentoId;
}
