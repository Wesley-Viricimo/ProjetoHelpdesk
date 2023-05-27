package com.wesley.helpdesk.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wesley.helpdesk.domain.Tecnico;
import com.wesley.helpdesk.domain.dtos.TecnicoDTO;
import com.wesley.helpdesk.services.TecnicoService;

@RestController //Definindo que a classe será um controlador rest para realizar requisições
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService service;
	
	@GetMapping(value = "/{id}")//Informando que estou recebendo uma variável de path
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) { //Response entity significa que queremos representar toda a resposta http, sendo possível controlar qualquer coisa envolvendo a requisição
		
		Tecnico obj = service.findById(id);
		
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}
	
}
