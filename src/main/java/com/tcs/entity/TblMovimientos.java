package com.tcs.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@Table(name ="tbl_movimientos", schema = "public")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblMovimientos{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name="fecha")
	private String fecha;
	
	@Column(name="tipo_movimiento")
	private String tipoMovimiento;
	
	@Column(name="valor")
	private BigDecimal valor;
	
	@Column(name="saldo")
	private BigDecimal saldo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cuenta")
	private TblCuentas tblCuentas;
    
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
