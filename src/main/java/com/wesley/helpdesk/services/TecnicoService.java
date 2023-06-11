package com.wesley.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wesley.helpdesk.domain.Pessoa;
import com.wesley.helpdesk.domain.Tecnico;
import com.wesley.helpdesk.domain.dtos.TecnicoDTO;
import com.wesley.helpdesk.repositories.PessoaRepository;
import com.wesley.helpdesk.repositories.TecnicoRepository;
import com.wesley.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.wesley.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoarepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		//Optional define que o id pode ou não ser encontrado
		Optional<Tecnico> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);//Buscar o id que foi informado na rota, e se não existir lançará a exceção já criada
		validaPorCpfEEmail(objDTO);//Validar se o CPF ou Email que estão sendo informados já existem
		oldObj = new Tecnico(objDTO);//Se não existir, criar um novo objeto com as novas informações que foram passadas
		return repository.save(oldObj);//Salvar estas novas informações
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);//Buscar técnico pelo id informado
		if(obj.getChamados().size() > 0) { //Se o técnico possuir um algum chamado o mesmo não será deletado
			throw new DataIntegrityViolationException("O técnico possui ordens de serviço e não pode ser deletado!!");
		} 
		
		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoarepository.findByCpf(objDTO.getCpf());//Realizando busca por cpf já cadastrado
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {//Se o CPF já estiver cadastrado, irá retornar uma exceção customizada
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema: " + objDTO.getCpf());
		}
		
		obj = pessoarepository.findByEmail(objDTO.getEmail()); //Realizando busca por email já cadastrado
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {//Se o email já estiver cadastrado , irá retornar uma exceção customizada
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema: " + objDTO.getEmail());
		}
	}
	
}
