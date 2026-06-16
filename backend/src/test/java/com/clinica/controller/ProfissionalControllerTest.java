package com.clinica.controller;

import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.ProfissionalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class ProfissionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfissionalRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void deveCriarProfissional() throws Exception {
        String json = """
                {"nome":"Dr. João","telefone":"11999","endereco":"Rua A","categoria":"MEDICO"}
                """;

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Dr. João"))
                .andExpect(jsonPath("$.categoria").value("MEDICO"));
    }

    @Test
    void deveRetornar400ParaCampoInvalido() throws Exception {
        String json = """
                {"nome":"","telefone":"11999","endereco":"Rua A","categoria":"MEDICO"}
                """;

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarPorCategoria() throws Exception {
        saveProfissional("Ana", CategoriaEnum.PSICOLOGO);
        saveProfissional("Carlos", CategoriaEnum.MEDICO);

        mockMvc.perform(get("/profissionais").param("categoria", "PSICOLOGO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ana"));
    }

    @Test
    void deveExcluirProfissional() throws Exception {
        ProfissionalDeSaude p = saveProfissional("Dr. X", CategoriaEnum.MEDICO);

        mockMvc.perform(delete("/profissionais/" + p.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/profissionais/" + p.getId()))
                .andExpect(status().isNotFound());
    }

    private ProfissionalDeSaude saveProfissional(String nome, CategoriaEnum cat) {
        ProfissionalDeSaude p = new ProfissionalDeSaude();
        p.setNome(nome);
        p.setTelefone("11999");
        p.setEndereco("Rua A");
        p.setCategoria(cat);
        return repository.save(p);
    }
}
