package com.clinica.dto;

import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfissionalResponseDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private CategoriaEnum categoria;

    public ProfissionalResponseDTO(ProfissionalDeSaude entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.telefone = entity.getTelefone();
        this.endereco = entity.getEndereco();
        this.categoria = entity.getCategoria();
    }
}
