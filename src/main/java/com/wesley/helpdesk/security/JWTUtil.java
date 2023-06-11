package com.wesley.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.expiration}") //Recuperando o valor da aplicattion properties e adicionando à variável
	private Long expiration;
	
	@Value("${jwt.secret}") //Recuperando o valor da aplicattion properties e adicionando à variável
	private String secret;

	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)//setSubjects define o valor de assunto de reinvidicações do token
				.setExpiration(new Date(System.currentTimeMillis() + expiration))//Data de expiração do token
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())//Algoritmo que será utilizado para a assinatura do token
				.compact();//Compacta o corpo do JWT deixando a API mais performática
	}
}
