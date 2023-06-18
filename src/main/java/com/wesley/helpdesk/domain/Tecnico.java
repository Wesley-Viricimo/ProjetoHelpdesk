package com.wesley.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wesley.helpdesk.domain.dtos.TecnicoDTO;
import com.wesley.helpdesk.domain.enums.Perfil;

@Entity(name="T_TECNICO")
public class Tecnico extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	//Clientes e tecnicos poderão possuir uma lista de chamados
	@JsonIgnore //Setando que quando for feita uma requisição nas informações do técnico, ignorar os chamados vinculados ao mesmo
	@OneToMany(mappedBy = "tecnico") //Definindo que um tecnico poderá ter vários chamados(1 para muitos) e está mapeado no atributo tecnico
	private List<Chamado> chamados = new ArrayList<>();

	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO);//Sempre que um tecnico for adicionado será adicionado o perfil tecnico ao mesmo
	}
	
	public Tecnico(TecnicoDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}


	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO);//Sempre que um tecnico for adicionado será adicionado o perfil tecnico ao mesmo
		addPerfil(Perfil.ADMIN);//Sempre que um tecnico for adicionado será adicionado o perfil admin ao mesmo
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
}
