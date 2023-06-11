package com.wesley.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesley.helpdesk.domain.dtos.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {//Quando é criada uma classe que extende de UsernamePasswordAuthenticationFilter o Spring entende automaticamente que o filtro irá interceptar a requisição post no endpoint /login, que é um endpoint reservado do spring security
	
	private AuthenticationManager authenticationManager; //Principal interface de estratégia para autenticação, se o principal (usuário e senha) da autenticação de entrada for válido e verificado, o método que ele possui chamado authenticate retorna uma instância de authentication com sinalizador autenticado definido como verdadeiro
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
				throws AuthenticationException {//Método que será chamado quando a aplicação tentar autenticar no endpoint /login
			try {
				CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class); //Convertendo os valores informados no corpo da requisição na classe CredenciaisDTO
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(),new ArrayList<>());//Criação do objeto UsernamePasswordAuthenticationToken que espera 3 argumentos, sendo principal (usuario), credentials (senha) e uma lista de authorities
				Authentication authentication = authenticationManager.authenticate(authenticationToken);//Instanciando objeto authentication e passando o authenticationToken como argumento
				return authentication;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	
	
	@Override
		protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
				Authentication authResult) throws IOException, ServletException {
			String username = ((UserSS) authResult.getPrincipal()).getUsername();
			String token = jwtUtil.generateToken(username);
			
			response.setHeader("access-control-expose-headers", "Authorization");
			response.setHeader("Authorization", "Bearer " + token);
		}
	
	
	@Override
		protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException failed) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

	private CharSequence json() {
		long date = new Date().getTime();
		return "{"
				+ "\"timestamp\": " + date + ", "
				+ "\"status\":  401, "
				+ "\"error\": \"Não autorizado\", "
				+ "\"message\": \"Email ou senha inválidos\", "
				+ "\"path\": \"/login\"}";
	}
}
