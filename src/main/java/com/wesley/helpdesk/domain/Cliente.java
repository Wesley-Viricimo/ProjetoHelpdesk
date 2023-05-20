package com.wesley.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.wesley.helpdesk.domain.enums.Perfil;

@Entity(name="T_CLIENTE")
public class Cliente extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	//Clientes e tecnicos poderão possuir uma lista de chamados
	@OneToMany(mappedBy = "cliente") //Definindo que um cliente poderá ter vários chamados(1 para muitos) e está mapeado no atributo cliente
	private List<Chamado> chamados = new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);//Sempre que um cliente for adicionado será adicionado o perfil cliente ao mesmo
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
	
}
