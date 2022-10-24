package com.noname.security.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noname.security.dto.CredencialDTO;

public class JWTFiltroAutenticador extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTTokenAuthenticService jwtTokenGerador;

	public JWTFiltroAutenticador(AuthenticationManager authenticationManager, JWTTokenAuthenticService jwtTokenGerador) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenGerador = jwtTokenGerador;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			CredencialDTO credencialDto = new ObjectMapper().readValue(request.getInputStream(),
					CredencialDTO.class);
			UsernamePasswordAuthenticationToken tokenAuthentication = new UsernamePasswordAuthenticationToken(
					credencialDto.getEmail(), credencialDto.getPassword(), new ArrayList<>());

			Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
			return authentication;

		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication resultadoAutenticacao) throws IOException, ServletException {
		UserDetails userSpring = (UserDetails) resultadoAutenticacao.getPrincipal();
		String emailUser = userSpring.getUsername();
		String jwtToken = jwtTokenGerador.createToken(emailUser);
		response.addHeader("Authorization", "Bearer " + jwtToken);
	}
}