package com.wesley.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PreAuthorize("hasAnyRole('ADMIN')")//Indicando que qualquer usuário que tenha a role (função) admin, pode acessar a rota para criar um tecnico 
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){ //Anotação valid irá identificar que as propriedades do objeto TecnicoDTO recebido como parâmetro possui validações e anotação request body define que o corpo da requisição é um tecnico DTO
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")//Indicando que qualquer usuário que tenha a role (função) admin, pode acessar a rota para atualizar um tecnico 
	@PutMapping(value = "/{id}")//Id deverá ser informado na rota quando usuário for atualizar um técnico o que também significa que está sendo recebida uma variável de path(caminho)
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){//Como estou recebendo uma variável de path tenho que adicionar o tipo da variável, e com a anotação valid eu valido se todos os campos serão preenchidos no update e com a anotação requestBody eu recebo as informações atualizadas do técnico
		Tecnico oldObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new TecnicoDTO(oldObj));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")//Indicando que qualquer usuário que tenha a role (função) admin, pode acessar a rota para atualizar um tecnico 
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
