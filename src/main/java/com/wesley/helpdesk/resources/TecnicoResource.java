package com.wesley.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		
		Tecnico obj = service.findById(id);//Encontrando técnico pelo Id
		
		return ResponseEntity.ok().body(new TecnicoDTO(obj));//Método retorna um DTO ou DATA ACESS OBJECT que é considerada uma boa prática de programação
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		List<Tecnico> list = service.findAll();
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@RequestBody TecnicoDTO objDTO){
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
