package com.neoris.turnosrotativos.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "conceptos")
@Getter @Setter 
public class Concepto {

    @Id
    @Column( nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column( nullable = false)
    private String nombre;
    
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer hsMinimo;
    
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer hsMaximo;
    
    @Column( nullable = false)
    private Boolean laborable;
    
    @OneToMany(mappedBy="concepto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Jornada> jornadas;

}
