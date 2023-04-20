package com.neoris.turnosrotativos.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jornadas")
@Getter @Setter 
public class Jornada {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="empleado_id")
	 @JsonIgnore
	 private Empleado empleado;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="concepto_id")
	 @JsonIgnore
	 private Concepto concepto;
	 
	 @Column( nullable = false)
	 private LocalDate fecha;
	 
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	 @Column
	 private Integer hsTrabajadas;

}
