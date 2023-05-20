package com.wesley.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wesley.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
