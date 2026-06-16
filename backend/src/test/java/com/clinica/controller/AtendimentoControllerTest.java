package com.clinica.controller;

import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.AtendimentoRepository;
import com.clinica.repository.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AtendimentoRepository repository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private ProfissionalDeSaude profissional;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        profissionalRepository.deleteAll();
        ProfissionalDeSaude p = new ProfissionalDeSaude();
        p.setNome("Dr. Teste");
        p.setTelefone("11999");
        p.setEndereco("Rua A");
        p.setCategoria(CategoriaEnum.MEDICO);
        profissional = profissionalRepository.save(p);
    }

    @Test
    void deveCriarAtendimento() throws Exception {
        String json = """
                {"data":"2025-01-10","horario":"14:00","problemaTexto":"Dor","receitaSaude":"Remedio","profissionalId":%d}
                """.formatted(profissional.getId());

        mockMvc.perform(post("/atendimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.profissionalId").value(profissional.getId()));
    }

    @Test
    void deveRetornar404ParaProfissionalInexistente() throws Exception {
        String json = """
                {"data":"2025-01-10","horario":"14:00","problemaTexto":"Dor","receitaSaude":"X","profissionalId":9999}
                """;

        mockMvc.perform(post("/atendimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarAtendimentos() throws Exception {
        mockMvc.perform(get("/atendimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
