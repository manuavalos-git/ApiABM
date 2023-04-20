package com.neoris.turnosrotativos.services;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.requests.JornadaRequest;
import com.neoris.turnosrotativos.responses.JornadaResponse;

public interface JornadaService {
	
	JornadaResponse addJornada(JornadaRequest jornada);

	List<Jornada> getByParams(BigInteger nroDocumento,LocalDate fecha);
}
