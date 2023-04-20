package com.neoris.turnosrotativos.services.imp;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.ConflictException;
import com.neoris.turnosrotativos.exceptions.NotFoundException;
import com.neoris.turnosrotativos.modelmappers.EmpleadoMapper;
import com.neoris.turnosrotativos.repositories.EmpleadoRepository;
import com.neoris.turnosrotativos.requests.EmpleadoRequest;
import com.neoris.turnosrotativos.responses.EmpleadoResponse;
import com.neoris.turnosrotativos.services.EmpleadoService;

@Service
public class EmpleadoServiceImp implements EmpleadoService{

	@Autowired
	EmpleadoRepository empleadoRepository;
	
	@Autowired
    EmpleadoMapper empleadoMapper;
	
	
	public EmpleadoResponse addEmpleado(EmpleadoRequest empleadoRequest) {
		Empleado empleado=empleadoMapper.mapToEmpleado(empleadoRequest);
		 // Validar si ya existe un empleado con el mismo número de documento o email
	    if (getByNroDocumento(empleado.getNroDocumento()).isPresent()) {
	        throw new ConflictException("Ya existe un empleado con el documento ingresado.");
	    } 
	    if (getByEmail(empleado.getEmail()).isPresent()) {
	        throw new ConflictException("Ya existe un empleado con el email ingresado.");
	    }
	    
		return empleadoMapper.mapToEmpleadoResponse( empleadoRepository.save(empleado));
	}

	@Override
	public List<EmpleadoResponse> getEmpleados() {
		return empleadoMapper.mapToEmpleadoResponseList(empleadoRepository.findAll());
	}

	@Override
	public Optional<Empleado> getByNroDocumento(BigInteger nroDocumento) {
		return empleadoRepository.findByNroDocumento(nroDocumento);
	
	}

	@Override
	public Optional<Empleado> getByEmail(String email) {
		return empleadoRepository.findByEmail(email);
	}

	@Override
	public Optional<Empleado> getById(Integer id) {
		// Buscamos el empleado por su id
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        // Verificamos si existe el empleado con el id proporcionado
        if(empleado.isPresent()) {
        	// Si encontramos el empleado, devolvemos una respuesta con el objeto y un estado de éxito
        	return empleado;
        } else {
        	// Si no encontramos el empleado con el id proporcionado, devolvemos un error de no encontrado
        	String errorMessage = String.format("No se encontró el empleado con Id: %d", id);
        	throw new NotFoundException(errorMessage);
        }
	}
	

	@Override
	public void deleteEmpleado(Integer id) {
		// Buscamos el empleado a eliminar por su id
		 empleadoRepository.delete(getById(id).get());
	}

	@Override
	public EmpleadoResponse putEmpleado(Integer id, EmpleadoRequest empleadoRequest) {
			Empleado empleado=empleadoMapper.mapToEmpleado(empleadoRequest);
			 // Buscamos el empleado a actualizar por su id
	        Empleado empleadoAnterior = getById(id).get();
        	  // Verificamos si ya existe un empleado con el mismo número de documento o correo electrónico
        	Optional<Empleado> empleadoNroDocumentoExistente = getByNroDocumento(empleado.getNroDocumento());
            Optional<Empleado> empleadoEmailExistente = getByEmail(empleado.getEmail());
            // Si ya existe un empleado con el mismo número de documento, devolvemos un error de conflicto
        	if (empleadoNroDocumentoExistente.isPresent() && !empleadoNroDocumentoExistente.get().getId().equals(id)) {
    	        throw new ConflictException("Ya existe un empleado con el documento ingresado.");
            } 
        	 // Si ya existe un empleado con el mismo email, devolvemos un error de conflicto
            if (empleadoEmailExistente.isPresent() && !empleadoEmailExistente.get().getId().equals(id)) {
            	throw new ConflictException("Ya existe un empleado con el email ingresado.");
            } else {
            	 // Si no existe un empleado con el mismo número de documento o correo electrónico, procedemos a actualizar el empleado existente
         		empleadoAnterior.setNroDocumento(empleado.getNroDocumento());
                 empleadoAnterior.setNombre(empleado.getNombre());
                 empleadoAnterior.setApellido(empleado.getApellido());
                 empleadoAnterior.setEmail(empleado.getEmail());
                 empleadoAnterior.setFechaNacimiento(empleado.getFechaNacimiento());
                 empleadoAnterior.setFechaIngreso(empleado.getFechaIngreso());
         		return empleadoMapper.mapToEmpleadoResponse(empleadoRepository.save(empleadoAnterior));
         	}
        }

}
