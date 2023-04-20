package com.neoris.turnosrotativos.repositories;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neoris.turnosrotativos.entities.Jornada;

public interface JornadaRepository extends JpaRepository<Jornada, Integer>{
 
	@Query("SELECT j FROM Jornada j WHERE (:fecha is null OR j.fecha = :fecha) AND (:nroDocumento is null OR j.empleado.nroDocumento = :nroDocumento)")
	List<Jornada> findByParams( @Param("nroDocumento") BigInteger nroDocumento,@Param("fecha") LocalDate fecha);

  
	List<Jornada> findByEmpleado_NroDocumentoAndFechaBetween(BigInteger nroDocumento, LocalDate inicioSemana, LocalDate finSemana);
}