package com.neoris.turnosrotativos.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.modelmappers.EmpleadoMapper;
import com.neoris.turnosrotativos.requests.EmpleadoRequest;
import com.neoris.turnosrotativos.services.EmpleadoService;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
    EmpleadoService serviceEmpleado;
	
	@PostMapping
	public ResponseEntity<Object> addEmpleado(@Valid @RequestBody EmpleadoRequest empleado) {
	    // Agregar el nuevo empleado y devolver una respuesta con el empleado agregado y el estado de respuesta creado
	    return ResponseEntity.status(HttpStatus.CREATED)
	                         .body(serviceEmpleado.addEmpleado(empleado));
	}
	
	@GetMapping
    public ResponseEntity<Object> getEmpleados() {
	    // Recupera una lista de todos los empleados
	    return new ResponseEntity<>(serviceEmpleado.getEmpleados(), HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Object> getEmpleado(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(new EmpleadoMapper().mapToEmpleadoResponse(serviceEmpleado.getById(id).get()), HttpStatus.OK);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteEmpleado(@PathVariable("id") Integer id) {
		serviceEmpleado.deleteEmpleado(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> putEmpleado(@PathVariable("id") Integer id,@Valid @RequestBody EmpleadoRequest empleado) {
		return new ResponseEntity<>(serviceEmpleado.putEmpleado(id,empleado), HttpStatus.OK);
    }
}
