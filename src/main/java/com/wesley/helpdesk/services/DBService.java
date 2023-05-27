package com.wesley.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesley.helpdesk.domain.Chamado;
import com.wesley.helpdesk.domain.Cliente;
import com.wesley.helpdesk.domain.Tecnico;
import com.wesley.helpdesk.domain.enums.Perfil;
import com.wesley.helpdesk.domain.enums.Prioridade;
import com.wesley.helpdesk.domain.enums.Status;
import com.wesley.helpdesk.repositories.ChamadoRepository;
import com.wesley.helpdesk.repositories.ClienteRepository;
import com.wesley.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	//Injetando dependências das interfaces criadas
		@Autowired
		private TecnicoRepository tecnicoRepository;
		@Autowired
		private ClienteRepository clienteRepository;
		@Autowired
		private ChamadoRepository chamadoRepository;
		

	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Wesley Viricimo", "12353677434", "wesley@teste1.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Jessica Viricimo", "12345157434", "jessica@teste1.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec3 = new Tecnico(null, "Ana Jullia Viricimo", "12309677434", "julia@teste1.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Sidineia Viricimo", "16409728321", "sidineia@teste1.com", "123");
		Cliente cli2 = new Cliente(null, "Sheila Viricimo", "16463278321", "Sheila@teste1.com", "123");
		Cliente cli3 = new Cliente(null, "Alessandra Amazonas", "16444009621", "alessandra@teste1.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 02", "Segundo Chamado", tec2, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado 03", "Terceiro Chamado", tec3, cli3);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	}
}
