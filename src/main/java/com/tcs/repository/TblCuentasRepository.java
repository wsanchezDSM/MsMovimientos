package com.tcs.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.tcs.entity.TblCuentas;


@Repository
public interface TblCuentasRepository extends JpaRepository<TblCuentas,Long>,JpaSpecificationExecutor<TblCuentas> {
	
	List<TblCuentas> findAllByEstadoTrue();
	
	Optional<TblCuentas> findByIdAndEstadoTrue(Long id);
	
	Boolean existsByNumeroCuentaAndTipoAndEstadoTrueAndNombreUsuario(String numeroCuenta, String tipo, String nombreUsuario);
	
}
