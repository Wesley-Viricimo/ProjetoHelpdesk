package com.wesley.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesley.helpdesk.domain.Chamado;
import com.wesley.helpdesk.repositories.ChamadoRepository;
import com.wesley.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id); //Optional define que o objeto pode ou não ser encontrado
		return obj.orElseThrow(() -> new ObjectNotFoundException("O chamado '" + id +  "' não foi encontrado!")); //Se o objeto não for encontrado será disparada a exceção customizada
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}
}
