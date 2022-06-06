package com.parcial3.app.bda;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parcial3.app.variables.Cliente;


@Repository
public class Clientebda implements IClientebda {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional (readOnly = true)
	public List<Cliente> findAll() {
		
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void saved(Cliente cliente) {
		if (cliente.getId() != null && cliente.getId() >0)
		{
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		em.remove(findOne(id));
		
	}

	@Override
	@Transactional
	public Cliente findOne(Long id) {
		
		return em.find(Cliente.class, id);
	}

	@Override
	public void guardar(@Valid Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	
}
