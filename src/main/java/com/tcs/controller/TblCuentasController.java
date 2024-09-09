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

import com.tcs.entity.TblCuentas;
import com.tcs.services.TblCuentasService;


@RestController
@RequestMapping("/api/cuentas/")
public class TblCuentasController {

	@Autowired
	private TblCuentasService tblCuentasService;
	
	@PostMapping
	public HashMap<String, Object> creaCuenta(@RequestBody TblCuentas body) throws RuntimeException, Exception{
		return tblCuentasService.creaCuenta(body);
	}
	
	@GetMapping
	public HashMap<String, Object> obtieneCuenta(@RequestParam Long id){
		return tblCuentasService.obtieneCuenta(id);
	}
	
	@PutMapping
	public HashMap<String, Object> actualizaCuenta(@RequestBody TblCuentas body) throws RuntimeException, Exception{
		return tblCuentasService.actualizaCuenta(body);
	}
	
	@DeleteMapping
	public HashMap<String, Object> eliminaCuenta(@RequestParam Long id) throws RuntimeException, Exception{
		return tblCuentasService.eliminaCuenta(id);
	}
}
