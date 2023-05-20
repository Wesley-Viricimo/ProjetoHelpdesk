package com.wesley.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wesley.helpdesk.domain.Chamado;
import com.wesley.helpdesk.domain.Cliente;
import com.wesley.helpdesk.domain.Tecnico;
import com.wesley.helpdesk.domain.enums.Perfil;
import com.wesley.helpdesk.domain.enums.Prioridade;
import com.wesley.helpdesk.domain.enums.Status;
import com.wesley.helpdesk.repositories.ChamadoRepository;
import com.wesley.helpdesk.repositories.ClienteRepository;
import com.wesley.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{ //CommandLineRunner implementa o método run que por sua vez é executado sempre que a aplicação é iniciada

	//Injetando dependências das interfaces criadas
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Wesley Viricimo", "12345677410", "wesley@teste1.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Jessica Viricimo", "16444278911", "jessica@teste1.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
