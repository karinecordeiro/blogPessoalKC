package org.generation.blogpessoal.security;

//essa classe sempre será a mesma, só mudará as variaveis.


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //------ habilitar nossa anotação

public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	// essa função vai estar acessivel a minha segurança
	//alias = apelido (auth)
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{ //Trows tratativa de erros

		//isso aqui (embaixo) não existe em sistemas empresariais
		auth.userDetailsService(userDetailsService);
		auth.inMemoryAuthentication()
		.withUser("root")
		.password (passwordEncoder().encode("root"))
		.authorities("ROLE USER");
		}
		
		@Bean // pra entender o passwordEncoder
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
	@Override 
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
		.antMatchers("/usuario/logar").permitAll()
		.antMatchers("/usuario/cadastrar").permitAll()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //não guarda sessão nenhuma
		.and().cors()
		.and().csrf().disable();
	}
	
}


