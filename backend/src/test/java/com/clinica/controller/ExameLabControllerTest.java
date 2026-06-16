package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.AtendimentoRepository;
import com.clinica.repository.ExameLabRepository;
import com.clinica.repository.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExameLabControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExameLabRepository repository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        atendimentoRepository.deleteAll();
        profissionalRepository.deleteAll();

        ProfissionalDeSaude p = new ProfissionalDeSaude();
        p.setNome("Dr. Teste");
        p.setTelefone("11999");
        p.setEndereco("Rua A");
        p.setCategoria(CategoriaEnum.MEDICO);
        p = profissionalRepository.save(p);

        Atendimento a = new Atendimento();
        a.setData(LocalDate.of(2025, 1, 10));
        a.setHorario(LocalTime.of(14, 0));
        a.setProblemaTexto("Dor");
        a.setReceitaSaude("Remedio");
        a.setProfissional(p);
        atendimento = atendimentoRepository.save(a);
    }

    @Test
    void deveCriarExame() throws Exception {
        String json = """
                {"descricao":"Hemograma","atendimentoId":%d}
                """.formatted(atendimento.getId());

        mockMvc.perform(post("/exames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Hemograma"))
                .andExpect(jsonPath("$.atendimentoId").value(atendimento.getId()));
    }

    @Test
    void deveRetornar404ParaAtendimentoInexistente() throws Exception {
        String json = """
                {"descricao":"Hemograma","atendimentoId":9999}
                """;

        mockMvc.perform(post("/exames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarExames() throws Exception {
        mockMvc.perform(get("/exames"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
