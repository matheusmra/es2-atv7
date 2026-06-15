package com.clinica.repository;

import com.clinica.model.CategoriaEnum;
import com.clinica.model.ProfissionalDeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalDeSaude, Long> {

    List<ProfissionalDeSaude> findByNomeContainingIgnoreCase(String nome);

    List<ProfissionalDeSaude> findByCategoria(CategoriaEnum categoria);
}
