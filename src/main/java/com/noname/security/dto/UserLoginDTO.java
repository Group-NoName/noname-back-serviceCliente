package com.noname.security.dto;

import java.util.List;

import com.noname.security.entidades.RolesConfig;
import com.noname.security.entidades.UserCredencial;
import com.noname.security.entidades.UserModel;

import lombok.Data;

@Data
public class UserLoginDTO {
  private String id;
  private UserCredencial credencial = new UserCredencial();
  private List<RolesConfig> role;

  public UserLoginDTO(UserModel user) {
    this.id = user.getId();
	this.credencial.setEmail(user.getCredencial().getEmail());
    this.role = user.getAutenticacao();
  }

  public static UserLoginDTO create(UserModel user) {
    return new UserLoginDTO(user);
  }
}
