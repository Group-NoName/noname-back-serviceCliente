package com.noname.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.noname.security.jwt.JWTApiAutenticacaoFilter;
import com.noname.security.jwt.JWTFiltroAutenticador;
import com.noname.security.jwt.JWTTokenAuthenticService;
import com.noname.security.service.UserDetaisService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegurancaControle extends WebSecurityConfigurerAdapter{
		
	@Autowired
	private UserDetaisService userDetailsService;
	@Autowired
	private JWTTokenAuthenticService tokenService;
	
	private static final String[] rotasPublicas = {"/user/login", "/user/cadastro"};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.cors().and().csrf().disable();

		http.authorizeHttpRequests().antMatchers(rotasPublicas).permitAll().anyRequest().authenticated();
		
		http.addFilter(new JWTFiltroAutenticador(authenticationManager(), tokenService));
		http.addFilter(new JWTApiAutenticacaoFilter(authenticationManager(), tokenService, userDetailsService));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder autenticador) throws Exception{
		autenticador.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
