package com.noname.security.dto;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.noname.security.entidades.RolesConfig;
import com.noname.security.entidades.UserCredencial;
import com.noname.security.entidades.UserModel;

import lombok.Data;

@Data
public class UsuarioDTO implements DataTransferObject<UserModel>{
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	private String email;
	private String password;
	private List<RolesConfig> autenticacao;
	
	@Override
	public UserModel get() {
		UserModel usuario = new UserModel();
		
		UserCredencial credencial = new UserCredencial();
		credencial.setEmail(email);
		credencial.setPassword(encoder.encode(password));
		
		usuario.setCredencial(credencial);
		usuario.setAutenticacao(autenticacao);
		return usuario;

	}
}