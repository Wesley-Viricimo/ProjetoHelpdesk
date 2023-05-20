package com.wesley.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wesley.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
