package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "atendimento")
@Getter
@Setter
@NoArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "Horário é obrigatório")
    @Column(nullable = false)
    private LocalTime horario;

    @NotBlank(message = "Descrição do problema é obrigatória")
    @Column(name = "problema_texto", nullable = false, columnDefinition = "TEXT")
    private String problemaTexto;

    @Column(name = "receita_saude", columnDefinition = "TEXT")
    private String receitaSaude;

    @NotNull(message = "Profissional é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeSaude profissional;

    /** Relacionamento 1:N — um atendimento possui vários exames laboratoriais */
    @OneToMany(mappedBy = "atendimento", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ExameLab> exames = new ArrayList<>();
}
