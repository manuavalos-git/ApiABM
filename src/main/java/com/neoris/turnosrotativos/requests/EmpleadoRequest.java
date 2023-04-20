package com.neoris.turnosrotativos.requests;

import java.math.BigInteger;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import com.neoris.turnosrotativos.customvalidations.Age;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class EmpleadoRequest {
	
	@NotNull(message = "'nroDocumento' es obligatorio.")
	private BigInteger nroDocumento;
	
	@NotBlank(message = "'nombre' es obligatorio.")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Solo se permiten letras en el campo 'nombre'.")
	private String nombre;
	
	@NotBlank(message = "'apellido' es obligatorio.")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Solo se permiten letras en el campo 'apellido'.")
	private String apellido;
	
	@NotBlank(message = "'email' es obligatorio.")
	@Email(message = "El email ingresado no es correcto.")
	private String email;
	
	@NotNull(message = "'fechaNacimiento' es obligatorio.")
	@PastOrPresent(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
	@Age(value = 18)
	private LocalDate fechaNacimiento;
	
	@NotNull(message = "'fechaIngreso' es obligatorio.")
	@PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
	private LocalDate fechaIngreso;
	
}
