package com.tcs.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.entity.TblCuentas;
import com.tcs.entity.TblMovimientos;

@Repository
public interface TblMovimientosRepository extends JpaRepository<TblMovimientos,Long>,JpaSpecificationExecutor<TblMovimientos> {
	
	List<TblMovimientos> findAllByEstadoTrue();
	
	Optional<TblMovimientos> findByIdAndEstadoTrue(Long id);
	
	Optional<TblMovimientos> findFirstByEstadoTrueAndTblCuentasOrderByIdDesc(TblCuentas tblCuentas);
	
	@Query("SELECT m FROM TblMovimientos m WHERE m.estado = true AND LOWER(m.tblCuentas.nombreUsuario) = LOWER(:nombreUsuario) AND m.fecha BETWEEN :fechaIni AND :fechaFin")
	List<TblMovimientos> findByNombreUsuarioAndFechas(@Param("nombreUsuario") String nombreUsuario, @Param("fechaIni") String fechaIni, @Param("fechaFin") String fechaFin);
}
