package com.neoris.turnosrotativos.entities;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "empleados")
@Getter @Setter 
public class Empleado {
	@Id
	@Column( nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column( nullable = false)
	private BigInteger nroDocumento;
	
	@Column( nullable = false)
	private String nombre;
	
	@Column(nullable= false)
	private String apellido;
	
	@Column( nullable = false)
	private String email;
	
	@Column( nullable = false)
	private LocalDate fechaNacimiento;

	@Column( nullable = false)
	private LocalDate fechaIngreso;
	
	@Column( nullable = false)
	private LocalDateTime fechaCreacion;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="empleado", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Jornada> jornadas;
	
	@PrePersist
    public void prePersist() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
           
        }
    }

}
