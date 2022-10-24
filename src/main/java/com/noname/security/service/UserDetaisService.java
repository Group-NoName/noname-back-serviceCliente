package com.noname.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noname.security.components.UsuarioSelecionadorEmailUsuario;
import com.noname.security.entidades.UserCredencial;
import com.noname.security.entidades.UserModel;
import com.noname.security.repositorios.UserRepositorio;

@Service
@Transactional
public class UserDetaisService implements UserDetailsService {
	
	@Autowired
	private UserRepositorio repositorio;
	
	@Autowired
	private UsuarioSelecionadorEmailUsuario selecionador;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<UserModel> usuarios = repositorio.findAll();
		UserModel usuario = selecionador.select(usuarios, email);
		if (usuario == null) {
			throw new UsernameNotFoundException(email);
		}
		UserCredencial credencial = usuario.getCredencial();
		return new UserDetailsImpl(credencial.getEmail(), credencial.getPassword(), usuario.getAutenticacao());
	}

}
