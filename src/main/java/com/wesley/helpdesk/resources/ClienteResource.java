package com.wesley.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wesley.helpdesk.domain.Cliente;
import com.wesley.helpdesk.domain.dtos.ClienteDTO;
import com.wesley.helpdesk.services.ClienteService;


@RestController //Definindo que a classe será um controlador rest para realizar requisições
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")//Informando que estou recebendo uma variável de path
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) { //Response entity significa que queremos representar toda a resposta http, sendo possível controlar qualquer coisa envolvendo a requisição
		
		Cliente obj = service.findById(id);//Encontrando cliente pelo Id
		
		return ResponseEntity.ok().body(new ClienteDTO(obj));//Método retorna um DTO ou DATA ACESS OBJECT que é considerada uma boa prática de programação
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO){ //Anotação valid irá identificar que as propriedades do objeto clienteDTO recebido como parâmetro possui validações e anotação request body define que o corpo da requisição é um cliente DTO
		Cliente newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")//Id deverá ser informado na rota quando usuário for atualizar um cliente o que também significa que está sendo recebida uma variável de path(caminho)
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){//Como estou recebendo uma variável de path tenho que adicionar o tipo da variável, e com a anotação valid eu valido se todos os campos serão preenchidos no update e com a anotação requestBody eu recebo as informações atualizadas do cliente
		Cliente oldObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ClienteDTO(oldObj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
