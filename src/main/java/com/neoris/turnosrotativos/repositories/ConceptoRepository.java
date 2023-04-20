package com.neoris.turnosrotativos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neoris.turnosrotativos.entities.Concepto;

public interface ConceptoRepository extends JpaRepository<Concepto, Integer>{

}