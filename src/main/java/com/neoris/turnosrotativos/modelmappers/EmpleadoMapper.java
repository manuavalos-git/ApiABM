package com.neoris.turnosrotativos.modelmappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.requests.EmpleadoRequest;
import com.neoris.turnosrotativos.responses.EmpleadoResponse;

@Component
public class EmpleadoMapper {
	private final ModelMapper modelMapper;

    public EmpleadoMapper() {
        this.modelMapper = new ModelMapper();
    }

    public EmpleadoResponse mapToEmpleadoResponse(Empleado empleado) {

        EmpleadoResponse empleadoResponse = modelMapper.map(empleado, EmpleadoResponse.class);
        empleadoResponse.setFechaCreacion(empleado.getFechaCreacion().toLocalDate());
        return empleadoResponse;
    }
    public List<EmpleadoResponse> mapToEmpleadoResponseList(List<Empleado> empleados) {
		 return empleados.stream().map(this::mapToEmpleadoResponse).collect(Collectors.toList());
	}
    public Empleado mapToEmpleado(EmpleadoRequest empleado) {
    	 Empleado empleadoReturn = modelMapper.map(empleado, Empleado.class);
         return empleadoReturn;
    }
}
