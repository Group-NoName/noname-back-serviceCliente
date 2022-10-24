package com.noname.security.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CredencialDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
}
