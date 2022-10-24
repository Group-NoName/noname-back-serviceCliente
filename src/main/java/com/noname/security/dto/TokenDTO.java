package com.noname.security.dto;

import com.noname.security.entidades.UserModel;

import lombok.Data;

@Data
public class TokenDTO {

  private String token;
  private String type;
  private UserLoginDTO user;

  public TokenDTO(String token, String type, UserModel user) {
    this.token = token;
    this.type = type;

    this.user = UserLoginDTO.create(user);
  }
}
