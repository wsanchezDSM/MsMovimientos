package com.tcs.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMovimientosClienteDTO {

	
	private String fecha;
	private String cliente;
	private String numeroCuenta;
	private String tipo;
	private Boolean estado;
	private BigDecimal movimiento;
	private BigDecimal saldoDisponible;
	
}
