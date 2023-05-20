package com.wesley.helpdesk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wesley.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
	

}
