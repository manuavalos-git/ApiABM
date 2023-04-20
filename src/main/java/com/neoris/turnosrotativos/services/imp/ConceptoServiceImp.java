package com.neoris.turnosrotativos.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.exceptions.NotFoundException;
import com.neoris.turnosrotativos.repositories.ConceptoRepository;
import com.neoris.turnosrotativos.services.ConceptoService;

@Service
public class ConceptoServiceImp implements ConceptoService{
	
	@Autowired
	ConceptoRepository conceptoRepository;
	

	@Override
	public List<Concepto> getConceptos() {
		return conceptoRepository.findAll();
	}

	@Override
	public Optional<Concepto> getById(Integer id) {
		 Optional<Concepto> concepto = conceptoRepository.findById(id);
	     // Verificamos si existe el empleado con el id proporcionado
        if(concepto.isPresent()) {
        	// Si encontramos el empleado, devolvemos una respuesta con el objeto y un estado de éxito
        	return concepto;
        } else {
        	// Si no encontramos el empleado con el id proporcionado, devolvemos un error de no encontrado
        	String errorMessage = String.format("No se encontró el concepto con Id: %d", id);
        	throw new NotFoundException(errorMessage);
        }
	}

}
