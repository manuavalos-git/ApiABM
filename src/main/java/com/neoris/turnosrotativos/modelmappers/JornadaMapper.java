package com.neoris.turnosrotativos.modelmappers;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.responses.JornadaResponse;

@Component
public class JornadaMapper {

    private final ModelMapper modelMapper;

    public JornadaMapper() {
        this.modelMapper = new ModelMapper();
    }

    public JornadaResponse mapToJornadaResponse(Jornada jornada) {
        JornadaResponse jornadaResponse = modelMapper.map(jornada, JornadaResponse.class);
        jornadaResponse.setNroDocumento(jornada.getEmpleado().getNroDocumento());
        jornadaResponse.setNombreCompleto(jornada.getEmpleado().getNombre()+" "+jornada.getEmpleado().getApellido());
        jornadaResponse.setConcepto(jornada.getConcepto().getNombre());
        return jornadaResponse;
    }
    private JornadaResponse mapToJornadaResponseSinId(Jornada jornada) {
        JornadaResponse jornadaResponse = modelMapper.map(jornada, JornadaResponse.class);
        jornadaResponse.setId(null);
        jornadaResponse.setNroDocumento(jornada.getEmpleado().getNroDocumento());
        jornadaResponse.setNombreCompleto(jornada.getEmpleado().getNombre()+" "+jornada.getEmpleado().getApellido());
        jornadaResponse.setConcepto(jornada.getConcepto().getNombre());
        return jornadaResponse;
    }

	public List<JornadaResponse> mapToJornadaResponseList(List<Jornada> jornadas) {
		 return jornadas.stream().map(this::mapToJornadaResponseSinId).collect(Collectors.toList());
	}
}
