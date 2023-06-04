package com.wesley.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesley.helpdesk.domain.Chamado;
import com.wesley.helpdesk.domain.Cliente;
import com.wesley.helpdesk.domain.Tecnico;
import com.wesley.helpdesk.domain.dtos.ChamadoDTO;
import com.wesley.helpdesk.domain.enums.Prioridade;
import com.wesley.helpdesk.domain.enums.Status;
import com.wesley.helpdesk.repositories.ChamadoRepository;
import com.wesley.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id); //Optional define que o objeto pode ou não ser encontrado
		return obj.orElseThrow(() -> new ObjectNotFoundException("O chamado '" + id +  "' não foi encontrado!")); //Se o objeto não for encontrado será disparada a exceção customizada
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) { //Método que irá disparar uma exceção, caso o chamado possuir algum técnico ou algum cliente que não exista
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		if(obj.getId() != null) { //Se o id do chamado for diferente de nulo significa que se trata de uma atualização do chamado
			chamado.setId(obj.getId());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}
}
