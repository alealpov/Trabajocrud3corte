package com.parcial3.app.bda;

import java.util.List;

import javax.validation.Valid;

import com.parcial3.app.variables.Cliente;



public interface IClientebda {
   
	public List<Cliente> findAll();
	
	public void saved(Cliente cliente);
	
	public void delete(Long id);
	
	public Cliente findOne(Long id);

	public void guardar(@Valid Cliente cliente);
}
