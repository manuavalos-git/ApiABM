package com.neoris.turnosrotativos.controllers;

import java.math.BigInteger;
import java.time.LocalDate;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.neoris.turnosrotativos.modelmappers.JornadaMapper;
import com.neoris.turnosrotativos.requests.JornadaRequest;
import com.neoris.turnosrotativos.services.JornadaService;

@RestController
@RequestMapping("/jornada")
public class JornadaController {

	@Autowired
	JornadaService serviceJornada;

	

	@PostMapping
	public ResponseEntity<Object> addJornada(@Valid @RequestBody JornadaRequest jornadaRequest) {
		
		// Agregar el nuevo empleado y devolver una respuesta con el empleado agregado y
		// el estado de respuesta creado
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceJornada.addJornada(jornadaRequest));
	}
	@GetMapping
	public ResponseEntity<Object> getJornadas(@RequestParam(required = false) BigInteger nroDocumento,
			 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
		
		return ResponseEntity.status(HttpStatus.OK).body(new JornadaMapper().mapToJornadaResponseList(serviceJornada.getByParams(nroDocumento, fecha)));
	}

}
