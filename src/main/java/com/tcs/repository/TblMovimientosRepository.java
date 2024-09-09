package com.tcs.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tcs.entity.TblCuentas;
import com.tcs.entity.TblMovimientos;

@Repository
public interface TblMovimientosRepository extends JpaRepository<TblMovimientos,Long>,JpaSpecificationExecutor<TblMovimientos> {
	
	List<TblMovimientos> findAllByEstadoTrue();
	
	Optional<TblMovimientos> findByIdAndEstadoTrue(Long id);
	
	Optional<TblMovimientos> findFirstEstadoTrueAndTblCuentasOrderByIdDesc(TblCuentas tblCuentas);
	
	List<TblMovimientos> findByEstadoTrueAndLowerTblCuentas_NombreUsuarioAndFechaBetween(String nombreUsuario, String fechaIni, String fechaFin);
}
