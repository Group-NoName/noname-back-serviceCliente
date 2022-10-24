package com.noname.security.components;

import java.util.List;

import org.springframework.stereotype.Component;

import com.noname.security.entidades.UserModel;
import com.noname.security.selects.UserSelecionador;

@Component
public class UsuarioSelecionadorEmailUsuario implements UserSelecionador<UserModel, String> {
	
	
	public UserModel select(List<UserModel> usuarios, String email) {
		UserModel selecionado = null;
		for (UserModel usuario : usuarios) {
			if (usuario.getCredencial().getEmail().equals(email)) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
	
	
	
	
}