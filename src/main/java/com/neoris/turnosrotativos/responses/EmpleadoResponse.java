package com.neoris.turnosrotativos.responses;

import java.math.BigInteger;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class EmpleadoResponse {
	
	private Integer id;
	
	private BigInteger nroDocumento;
	
	private String nombre;
	
	private String apellido;
	
	private String email;
	
	private LocalDate fechaNacimiento;

	private LocalDate fechaIngreso;
	
	private LocalDate fechaCreacion;
	
}
