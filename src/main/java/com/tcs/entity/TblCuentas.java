package com.tcs.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="tbl_cuentas_x_usuario", schema = "public")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblCuentas {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name="numero_cuenta")
	private String numeroCuenta;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="saldo_inicial")
	private BigDecimal saldoInicial;
	
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Column(name="nombre_usuario")
	private String nombreUsuario;
	
	@Column(name="saldo_actual")
	private BigDecimal saldoActual;
	
	@Column(name="estado")
	private Boolean estado;
	
	@Column(name="usuario_creacion")
	private String usuarioCreacion;
	
	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;
	
	@Column(name="usuario_modificacion")
	private String usuarioModificacion;
	
	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;
}
