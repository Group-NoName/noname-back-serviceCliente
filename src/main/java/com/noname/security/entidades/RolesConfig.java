package com.noname.security.entidades;

import org.springframework.security.core.GrantedAuthority;
import com.noname.security.roles.Roles;
import lombok.Data;

@Data
public class RolesConfig implements GrantedAuthority{
	private static final long serialVersionUID = 1L;
	
	private Roles role;

	@Override
	public String getAuthority() {
		return this.role.toString();
	}
	
}
