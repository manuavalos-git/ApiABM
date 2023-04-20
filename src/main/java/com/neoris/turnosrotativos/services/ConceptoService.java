package com.neoris.turnosrotativos.services;

import java.util.List;
import java.util.Optional;
import com.neoris.turnosrotativos.entities.Concepto;


public interface ConceptoService {
	
	List<Concepto> getConceptos();
	
	Optional<Concepto> getById(Integer id);
}
