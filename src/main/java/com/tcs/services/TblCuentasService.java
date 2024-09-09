package com.tcs.services;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.entity.TblCuentas;
import com.tcs.enums.EnumMensajes;
import com.tcs.repository.TblCuentasRepository;
import com.tcs.util.Utileria;

@Service
public class TblCuentasService {

	
	@Autowired
	private TblCuentasRepository tblCuentasRepository;

	
	@Autowired
	private Utileria util;
	
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public HashMap<String, Object> creaCuenta(TblCuentas body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.nonNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.API_INCORRECTA.getDescripcion().replace("{0}", "guardar"), null);
				return salida;
			}
			
			if(tblCuentasRepository.existsByNumeroCuentaAndTipoAndEstadoTrueAndNombreUsuario(body.getNumeroCuenta(), body.getTipo(), body.getNombreUsuario()))
			{
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.REGISTRO_EXISTE.getDescripcion(), null);
				return salida;
			}
			
			body.setSaldoActual(body.getSaldoInicial());
			body.setFechaCreacion(util.fechaActual());
			body.setFechaModificacion(null);
			body.setUsuarioModificacion(null);
			body.setEstado(Boolean.TRUE);
			tblCuentasRepository.save(body);
			salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_GUARDADO.getDescripcion(), null);
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;
	}

	public HashMap<String, Object> obtieneCuenta(Long id){
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replaceAll("{0}", "id").replaceAll("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblCuentas> clienteOpt =tblCuentasRepository.findByIdAndEstadoTrue(id);
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
	
	public TblCuentas seteaDataUsuario(TblCuentas regActual, TblCuentas body) {
		//regActual.setSaldoInicial(body.getSaldoInicial());
		regActual.setTipo(body.getTipo());
		regActual.setIdUsuario(body.getIdUsuario());
		regActual.setNombreUsuario(body.getNombreUsuario());
		regActual.setFechaModificacion(util.fechaActual());
		regActual.setUsuarioModificacion(body.getUsuarioModificacion());
	
		return regActual;
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public HashMap<String, Object> actualizaCuenta(TblCuentas body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replaceAll("{0}", "id").replaceAll("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblCuentas> clienteOpt =tblCuentasRepository.findById(body.getId());
			if (clienteOpt.isPresent()) { 
				TblCuentas userActual=clienteOpt.get();
				if(userActual.getEstado()) {
					tblCuentasRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else if(!userActual.getEstado() && body.getEstado()) {
					tblCuentasRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else
					salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replaceAll("{0}", body.getId().toString()), null);

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
	public HashMap<String, Object> eliminaCuenta(Long id)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replaceAll("{0}", "id").replaceAll("{1}", "eliminar el cliente"), null);
				return salida;
			}
			
			Optional<TblCuentas> clienteOpt =tblCuentasRepository.findByIdAndEstadoTrue(id);
			if (clienteOpt.isPresent()) {
				tblCuentasRepository.delete(clienteOpt.get());
				salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_ELIMINADO.getDescripcion(), null);
			}else{
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replaceAll("{0}",id.toString()), null);
			}
		} catch (Exception e) {
			salida= util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;

	}
}
