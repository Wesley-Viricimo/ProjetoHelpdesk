package com.wesley.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.wesley.helpdesk.security.JWTAuthenticationFilter;
import com.wesley.helpdesk.security.JWTUtil;

@EnableWebSecurity //Anotação já possui a anotação configuration, sendo assim não é necessário que seja adicionada
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	@Autowired
	private Environment env; //Environment é a interface que representa o ambiente no qual o aplicativo atual está sendo executado, então pode ser usado para obter os perfis e propriedades do ambiente da aplicação
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 if(Arrays.asList(env.getActiveProfiles()).contains("test")) { //Verifica se o perfil que está ativo é o perfil de teste
			 http.headers().frameOptions().disable(); //Se for, desativar os frames options
		 }
		 
		 http.cors().and().csrf().disable();//Indicando que possuo uma configuração de cors e desabilitando proteção contra ataque csrf (cross site request for get) que é um ataque baseado em armazenamento em sessões de usuário
		 http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));//Adicionando filtro criado
		 http.authorizeRequests() //Autorizando requisições
		 .antMatchers(PUBLIC_MATCHERS).permitAll()//Permitir tudo o que vier da variável
		 .anyRequest().authenticated();//Para qualquer outra requisição, precisa estar autenticado
	     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Assegurando que a sessão de usuário não será criada
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Sobrescrevendo metodo configure para informar que esremos usando a autenticação criada e o encoder que irá ser utilizado
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean //Quando o método está com a anotação @Bean, é executado quando o projeto é executado 
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();//Aplica permições de valores padrão à configuração do cors
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));//Metodos que serão permitidos
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);//Definindo que quero receber requisições de todas as fontes e registrando uma configuração de cors
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() { //Criando método responsável por encriptar senhas e poderá ser utilizado em qualquer ponto da aplicação
		return new BCryptPasswordEncoder();
	}
}
