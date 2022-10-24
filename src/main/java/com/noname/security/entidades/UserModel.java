package com.noname.security.entidades;

import java.util.List;



import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document
public class UserModel {
	
	private String id;
	private UserCredencial credencial;
	private List<RolesConfig> autenticacao;

}
