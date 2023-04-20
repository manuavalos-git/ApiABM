package com.neoris.turnosrotativos.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.requests.EmpleadoRequest;
import com.neoris.turnosrotativos.responses.EmpleadoResponse;


public interface EmpleadoService {
	

	    EmpleadoResponse addEmpleado(EmpleadoRequest empleadoRequest);

		List<EmpleadoResponse> getEmpleados();

		Optional<Empleado> getByNroDocumento(BigInteger bigInteger);

		Optional<Empleado> getByEmail(String email);

		Optional<Empleado> getById(Integer id);

		EmpleadoResponse putEmpleado(Integer id, EmpleadoRequest empleadoRequest);

		void deleteEmpleado(Integer id);


}
