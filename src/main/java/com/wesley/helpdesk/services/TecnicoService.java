package com.wesley.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesley.helpdesk.domain.Tecnico;
import com.wesley.helpdesk.repositories.TecnicoRepository;
import com.wesley.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		//Optional define que o id pode ou não ser encontrado
		Optional<Tecnico> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id));
	}
}
