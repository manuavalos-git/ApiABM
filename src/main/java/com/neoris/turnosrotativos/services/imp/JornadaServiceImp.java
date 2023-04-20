package com.neoris.turnosrotativos.services.imp;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.exceptions.BadRequestException;
import com.neoris.turnosrotativos.exceptions.NotFoundException;
import com.neoris.turnosrotativos.modelmappers.JornadaMapper;
import com.neoris.turnosrotativos.repositories.JornadaRepository;
import com.neoris.turnosrotativos.requests.JornadaRequest;
import com.neoris.turnosrotativos.responses.JornadaResponse;
import com.neoris.turnosrotativos.services.ConceptoService;
import com.neoris.turnosrotativos.services.EmpleadoService;
import com.neoris.turnosrotativos.services.JornadaService;

@Service
public class JornadaServiceImp implements JornadaService{

	@Autowired
	JornadaRepository jornadaRepository;

	@Autowired
	EmpleadoService serviceEmpleado;
	
	@Autowired
	ConceptoService serviceConcepto;
	
	@Autowired
	JornadaMapper jornadaMapper;
	
	@Override
	public JornadaResponse addJornada(JornadaRequest jornada) {
		Optional<Empleado> empleado;
		Optional<Concepto> concepto;
		try {
			empleado = serviceEmpleado.getById(jornada.getIdEmpleado());
			concepto = serviceConcepto.getById(jornada.getIdConcepto());
		}catch (Exception e) {
			if(e.getMessage().contains("empleado")) {
			throw new NotFoundException("No existe el empleado ingresado.");
			}else {
				throw new NotFoundException("No existe el concepto ingresado.");
			}
		}

		if ((concepto.get().getNombre().equals("Turno Normal") || concepto.get().getNombre().equals("Turno Extra"))&& jornada.getHsTrabajadas() == null) {
			throw new BadRequestException("'hsTrabajadas' es obligatorio para el concepto ingresado.");
		}
		if ((concepto.get().getNombre().equals("Dia Libre")) && jornada.getHsTrabajadas() != null) {
			throw new BadRequestException("El concepto ingresado no requiere el ingreso de 'hsTrabajadas'.");
		}
		// --------------------------------REGLAS DE NEGOCIO-----------------------------------
		String mensajeErrorReglaDeNegocio=validacionesReglasDeNegocioAdd(jornada, empleado, concepto);
		if(mensajeErrorReglaDeNegocio!=null) {
			throw new BadRequestException(mensajeErrorReglaDeNegocio);
		}
		// --------------------------------REGLAS DE NEGOCIO-----------------------------------
		Jornada newJornada = new Jornada();
		newJornada.setEmpleado(empleado.get());
		newJornada.setConcepto(concepto.get());
		newJornada.setFecha(jornada.getFecha());
		newJornada.setHsTrabajadas(jornada.getHsTrabajadas());
		
		return jornadaMapper.mapToJornadaResponse(jornadaRepository.save(newJornada));
	}

	
	
	private String validacionesReglasDeNegocioAdd(JornadaRequest jornada, Optional<Empleado> empleado, Optional<Concepto> concepto) {
		
		if ((concepto.get().getNombre().equals("Turno Normal") || concepto.get().getNombre().equals("Turno Extra"))
				&& jornada.getHsTrabajadas() != null) {
			int horasTrabajadas = jornada.getHsTrabajadas();
			if (horasTrabajadas < concepto.get().getHsMinimo() || horasTrabajadas > concepto.get().getHsMaximo()) {
				return "El rango de horas que se puede cargar para este concepto es de "
								+ concepto.get().getHsMinimo() + " - " + concepto.get().getHsMaximo();
			}
		}
		
		List<Jornada> jornadasEmpleado = getByParams(empleado.get().getNroDocumento(),
				jornada.getFecha());

		for (Jornada j : jornadasEmpleado) {

			if (jornada.getIdConcepto() == 3) {
				return "El empleado no puede cargar Dia Libre si cargo un turno previamente para la fecha ingresada.";
			}

			int totalHorasDiariasFecha = j.getHsTrabajadas()==null?jornada.getHsTrabajadas(): j.getHsTrabajadas()+ jornada.getHsTrabajadas();

			if (j.getConcepto().getNombre().equals("Dia Libre")) {
				return"El empleado ingresado cuenta con un día libre en esa fecha.";
			}
			if (j.getConcepto().getId().equals(jornada.getIdConcepto())) {
				return "El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.";
			}
			if (totalHorasDiariasFecha > 12) {
				return "El empleado no puede cargar más de 12 horas trabajadas en un día.";
			}
		}
		// un empleado no puede cargar más de 48 horas semanales
		List<Jornada> jornadasSemana = jornadaRepository.findByEmpleado_NroDocumentoAndFechaBetween(empleado.get().getNroDocumento(),
				jornada.getFecha().minusDays(6), jornada.getFecha());
		
		// un empleado no puede tener más de 2 días libres por semana
		long diasLibresSemana = jornadasSemana.stream()
				.filter(j -> j.getConcepto().getNombre().equals("Dia Libre")).count();
		
		if (concepto.get().getNombre().equals("Dia Libre") && diasLibresSemana >= 2) {
			return "El empleado no cuenta con más días libres esta semana.";
		}
		
		// un empleado no puede cargar más de 5 turnos normales en una semana
		int turnosNormalesSemana = (int) jornadasSemana.stream()
				.filter(j -> j.getConcepto().getNombre().equals("Turno Normal")).count();
		if (concepto.get().getNombre().equals("Turno Normal") && turnosNormalesSemana >= 5) {
				return "El empleado ingresado ya cuenta con 5 turnos normales esta semana.";
		}
		
		// un empleado no puede cargar más de 3 turnos extra en una semana
		int turnosExtraSemana = (int) jornadasSemana.stream()
				.filter(j -> j.getConcepto().getNombre().equals("Turno Extra")).count();
		if (concepto.get().getNombre().equals("Turno Extra") && turnosExtraSemana >= 3) {
			return "El empleado ingresado ya cuenta con 3 turnos extra esta semana.";
		}
		
		int totalHorasSemana = jornadasSemana.stream()
			    .mapToInt(j -> j.getHsTrabajadas()==null?0:j.getHsTrabajadas())
			    .sum();
		if (jornada.getIdConcepto()!=3 && totalHorasSemana + jornada.getHsTrabajadas() > 48) {
			return "El empleado ingresado supera las 48 horas semanales.";
		}
		return null;
	
	}

	@Override
	public List<Jornada> getByParams(BigInteger nroDocumento, LocalDate fecha) {
		return jornadaRepository.findByParams(nroDocumento,fecha);
	}

}
