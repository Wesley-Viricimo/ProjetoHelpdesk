package com.wesley.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
}
