package com.tcs.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.services.ReporteService;

@RestController
@RequestMapping("/api/reportes/")
public class ReportesController {

	@Autowired
	private ReporteService reporteService;
	
	@GetMapping
	public HashMap<String, Object> obtenerReportexFechas(@RequestParam String fecha, @RequestParam String cliente){
		return reporteService.obtenerReportexFechas(fecha,cliente);
	}
}
