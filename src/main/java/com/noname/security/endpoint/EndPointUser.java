package com.noname.security.endpoint;

import org.springframework.security.core.Authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noname.security.components.UsuarioSelecionadorEmailUsuario;
import com.noname.security.dto.TokenDTO;
import com.noname.security.dto.UsuarioDTO;
import com.noname.security.entidades.UserModel;
import com.noname.security.jwt.JWTTokenAuthenticService;
import com.noname.security.repositorios.UserRepositorio;
import com.noname.security.service.LoginService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class EndPointUser {

	@Autowired
	private UserRepositorio service;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTTokenAuthenticService tokenService;
	
	@Autowired
	private UsuarioSelecionadorEmailUsuario selecionador;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/home")
	public ResponseEntity<?> Home() {
		try{
			return new ResponseEntity<>("Bem vindo!", HttpStatus.OK);
		}catch(Exception e) {			
			return new ResponseEntity<>(e, HttpStatus.FORBIDDEN);
		}

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginService login){
		UsernamePasswordAuthenticationToken logging = login.convert();
		List<UserModel> usuarios = service.findAll();
		try {
			Authentication authentication = authManager.authenticate(logging);
						
			UserModel user = selecionador.select(usuarios, authentication.getName());
			
			String token = tokenService.createToken(authentication.getName());
						
			return new ResponseEntity<>(new TokenDTO(token, "Bearer", user),HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<>("Não foi possivel",HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> createUser(@RequestBody UsuarioDTO user){
		UserModel usuario = user.get();
		service.insert(usuario);
		return new ResponseEntity<>("User Criado", HttpStatus.ACCEPTED);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/usuarios")
	public ResponseEntity<?> getUsers(){
		List<UserModel> usuarios = service.findAll();
		if (usuarios.isEmpty()) {
			return new ResponseEntity<>("Nenhum Usuario Encontrado", HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<>(usuarios, HttpStatus.OK);
		}
	}
}
