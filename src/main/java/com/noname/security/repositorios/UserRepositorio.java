package com.noname.security.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.security.entidades.UserModel;

@Repository
public interface UserRepositorio extends MongoRepository<UserModel,String>{

}
