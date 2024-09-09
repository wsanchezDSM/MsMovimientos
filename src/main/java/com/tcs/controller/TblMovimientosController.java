package com.tcs.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.entity.TblMovimientos;
import com.tcs.services.TblMovimientosService;


@RestController
@RequestMapping("/api/movimientos/")
public class TblMovimientosController {

	@Autowired
	private TblMovimientosService tblUsuarioService;
	
	@PostMapping
	public HashMap<String, Object> creaMovimiento(@RequestBody TblMovimientos body) throws RuntimeException, Exception{
		return tblUsuarioService.creaMovimiento(body);
	}
	
	@GetMapping
	public HashMap<String, Object> obtieneMovimiento(@RequestParam Long id){
		return tblUsuarioService.obtieneMovimiento(id);
	}
	
	@PutMapping
	public HashMap<String, Object> actualizaMovimiento(@RequestBody TblMovimientos body) throws RuntimeException, Exception{
		return tblUsuarioService.actualizaMovimiento(body);
	}
	
	@DeleteMapping
	public HashMap<String, Object> eliminaMovimiento(@RequestParam Long id) throws RuntimeException, Exception{
		return tblUsuarioService.eliminaMovimiento(id);
	}
}
