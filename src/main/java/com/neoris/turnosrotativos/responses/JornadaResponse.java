package com.neoris.turnosrotativos.responses;

import java.math.BigInteger;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class JornadaResponse {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	 private Integer id;
	 
	 private BigInteger nroDocumento;
	
	 private String nombreCompleto;
	
	 private LocalDate fecha;

	 private String concepto;
	
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	 private Integer hsTrabajadas;
	
}
