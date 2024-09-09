package com.tcs.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.entity.TblCuentas;
import com.tcs.entity.TblMovimientos;
import com.tcs.enums.EnumMensajes;
import com.tcs.repository.TblCuentasRepository;
import com.tcs.repository.TblMovimientosRepository;
import com.tcs.util.Utileria;

@Service
public class TblMovimientosService {

	@Autowired
	private TblMovimientosRepository tblMovimientosRepository;
	
	@Autowired
	private TblCuentasRepository tblCuentasRepository;
	
	@Autowired
	private Utileria util;
	
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public HashMap<String, Object> creaMovimiento(TblMovimientos body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.nonNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.API_INCORRECTA.getDescripcion().replace("{0}", "guardar"), null);
				return salida;
			}
			
			if(Objects.isNull(body.getTblCuentas())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "Cuentas").replace("{1}", "Guardar"), null);
				return salida;
			}

			Optional<TblCuentas> opCuentas=tblCuentasRepository.findByIdAndEstadoTrue(body.getTblCuentas().getId());
			if(opCuentas.isPresent()) {
				Optional<TblMovimientos> opMovimientoPrevio=tblMovimientosRepository.findFirstByEstadoTrueAndTblCuentasOrderByIdDesc(opCuentas.get());
				if(opMovimientoPrevio.isPresent()) {
					BigDecimal operacion=body.getValor().add(opMovimientoPrevio.get().getSaldo());
					if(operacion.compareTo(BigDecimal.ZERO)<0) {
						salida=util.salidaDatos(Boolean.FALSE,EnumMensajes.SALDO_NO_DISPONIBLE.getDescripcion() , null);
						return salida;
					}else {
						body.setSaldo(operacion);
						body.setTblCuentas(opCuentas.get());
					}
				}else {
					body.setSaldo(body.getValor());
					body.setTblCuentas(opCuentas.get());
				}
			}else{
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}", "Cuentas"), null);
				return salida;
			}
			
			body.setFechaCreacion(util.fechaActual());
			body.setFechaModificacion(null);
			body.setUsuarioModificacion(null);
			body.setEstado(Boolean.TRUE);
			tblMovimientosRepository.save(body);
			salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_GUARDADO.getDescripcion(), null);
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;
	}

	public HashMap<String, Object> obtieneMovimiento(Long id){
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblMovimientos> clienteOpt =tblMovimientosRepository.findByIdAndEstadoTrue(id);
			if (clienteOpt.isPresent()) 
		       salida = util.salidaDatos(Boolean.TRUE, 
		    		   EnumMensajes.OK.getDescripcion(), 
		    		   clienteOpt.get());
		    else 
		       salida = util.salidaDatos(
		                Boolean.FALSE, 
		                EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}", id.toString()), 
		                null
		            );
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;

	}
	
	public TblMovimientos seteaDataUsuario(TblMovimientos regActual, TblMovimientos body) {
		regActual.setFecha(body.getFecha());
		regActual.setTipoMovimiento(body.getTipoMovimiento());
		//regActual.setValor(body.getValor());
		regActual.setFechaModificacion(util.fechaActual());
		regActual.setUsuarioModificacion(body.getUsuarioModificacion());

		return regActual;
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public HashMap<String, Object> actualizaMovimiento(TblMovimientos body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "obtener info"), null);
				return salida;
			}
			
			if(Objects.isNull(body.getTblCuentas())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "Cuentas").replace("{1}", "Guardar"), null);
				return salida;
			}
			
			Optional<TblMovimientos> clienteOpt =tblMovimientosRepository.findById(body.getId());
			if (clienteOpt.isPresent()) {
				TblMovimientos userActual=clienteOpt.get();
				
				Optional<TblCuentas> opCuentas=tblCuentasRepository.findByIdAndEstadoTrue(body.getTblCuentas().getId());
				if(opCuentas.isPresent()) {
					Optional<TblMovimientos> opMovimientoPrevio=tblMovimientosRepository.findFirstByEstadoTrueAndTblCuentasOrderByIdDesc(opCuentas.get());
					if(opMovimientoPrevio.isPresent()) {
						BigDecimal operacion=body.getValor().add(opMovimientoPrevio.get().getSaldo());
						if(operacion.compareTo(BigDecimal.ZERO)<0) {
							salida=util.salidaDatos(Boolean.FALSE,EnumMensajes.SALDO_NO_DISPONIBLE.getDescripcion() , null);
							return salida;
						}else {
							body.setSaldo(operacion);
							body.setTblCuentas(opCuentas.get());
							//tblCuentasRepository.save(regActual);	
						}
					}else {
						body.setSaldo(body.getValor());
						body.setTblCuentas(opCuentas.get());
					}
					
				}else{
					salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}", "Cuentas"), null);
					return salida;
				}
				
				
				
				if(userActual.getEstado()) {
					tblMovimientosRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else if(!userActual.getEstado() && body.getEstado()) {
					tblMovimientosRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else
					salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}", body.getId().toString()), null);

			}else 
		       salida = util.salidaDatos(
		                Boolean.FALSE, 
		                EnumMensajes.NO_EXISTE.getDescripcion().replace("{0}", body.getId().toString()), 
		                null
		            );
			
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public HashMap<String, Object> eliminaMovimiento(Long id)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "eliminar el cliente"), null);
				return salida;
			}
			
			Optional<TblMovimientos> clienteOpt =tblMovimientosRepository.findByIdAndEstadoTrue(id);
			if (clienteOpt.isPresent()) {
				TblMovimientos movActual=clienteOpt.get();
				TblCuentas regActual=movActual.getTblCuentas();
				regActual.setSaldoActual(regActual.getSaldoActual().add(movActual.getValor()));
				if(regActual.getSaldoActual().compareTo(BigDecimal.ZERO)<0) {
					salida=util.salidaDatos(Boolean.FALSE,EnumMensajes.SALDO_NO_DISPONIBLE.getDescripcion() , null);
					return salida;
				}

				tblMovimientosRepository.delete(movActual);
				salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_ELIMINADO.getDescripcion(), null);
			}else{
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}",id.toString()), null);
			}
		} catch (Exception e) {
			salida= util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;

	}
	
}
