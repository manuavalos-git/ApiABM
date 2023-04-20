package com.neoris.turnosrotativos.repositories;

import java.math.BigInteger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.neoris.turnosrotativos.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
	
	Optional<Empleado> findByNroDocumento(BigInteger nroDocumento);

	Optional<Empleado> findByEmail(String email);

}
