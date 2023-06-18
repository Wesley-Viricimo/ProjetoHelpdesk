package com.wesley.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		
		if(claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if(username != null && expirationDate != null && now.before(expirationDate)) { //Verifica se o username é diferente de nulo, se a data de expiração do token é diferente de nulo e se o momento atual é antes da data de expiração
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
