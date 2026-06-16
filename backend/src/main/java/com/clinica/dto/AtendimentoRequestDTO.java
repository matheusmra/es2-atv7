package com.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class AtendimentoRequestDTO {

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Horário é obrigatório")
    private LocalTime horario;

    @NotBlank(message = "Descrição do problema é obrigatória")
    private String problemaTexto;

    private String receitaSaude;

    @NotNull(message = "Profissional é obrigatório")
    private Long profissionalId;
}
