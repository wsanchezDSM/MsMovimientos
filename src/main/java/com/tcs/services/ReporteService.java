package com.tcs.services;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.tcs.enums.EnumMensajes;
import com.tcs.models.ReporteMovimientosClienteDTO;
import com.tcs.repository.TblMovimientosRepository;
import com.tcs.util.Utileria;

@Service
public class ReporteService {

	@Autowired
	private TblMovimientosRepository tblMovimientosRepository;
	
	@Autowired
	private Utileria util;
	
	public HashMap<String, Object> obtenerReportexFechas(String fecha, String cliente){
		HashMap<String, Object> salida=new HashMap<>();
		try {
			List<ReporteMovimientosClienteDTO> lsSalida=tblMovimientosRepository.findByNombreUsuarioAndFechas(cliente,fecha.split("|")[0], fecha.split("|")[1])
					.stream().map(y->{
						ReporteMovimientosClienteDTO dataR=new ReporteMovimientosClienteDTO();
						dataR.setCliente(y.getTblCuentas().getNombreUsuario());
						dataR.setEstado(y.getEstado());
						dataR.setFecha(y.getFecha());
						dataR.setMovimiento(y.getValor());
						dataR.setSaldoDisponible(y.getSaldo());
						dataR.setTipo(y.getTipoMovimiento().toUpperCase());
						return dataR;
						
					}).collect(Collectors.toList());
			salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.OK.getDescripcion(), lsSalida);
					
		} catch (Exception e) {
			// TODO: handle exception
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);

		}
		return salida;
	}
}
