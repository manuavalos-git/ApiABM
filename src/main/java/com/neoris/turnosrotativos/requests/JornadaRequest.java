package com.neoris.turnosrotativos.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class JornadaRequest {
	@NotNull(message = "'idEmpleado' es obligatorio.")
	private Integer idEmpleado;
	
	@NotNull(message = "'idConcepto' es obligatorio.")
	private Integer idConcepto;
	
	@NotNull(message = "'fecha' es obligatorio.")
	private LocalDate fecha;
	
	private Integer hsTrabajadas;

}
