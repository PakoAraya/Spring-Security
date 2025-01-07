package com.segurito.spring.services;

import com.segurito.spring.dtos.UserLoginDTO;
import com.segurito.spring.interfaces.UserLogin;
import com.segurito.spring.repositories.UserLoginRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLoginServiceImpl implements UserLogin {

  @Autowired
  private UserLoginRepositoryJPA userLoginRepositoryJPA;

  public UserLoginServiceImpl(UserLoginRepositoryJPA userLoginRepositoryJPA){
    this.userLoginRepositoryJPA = userLoginRepositoryJPA;
  }

  @Override
  public List<UserLoginDTO> findUserLoginById(Long id) {
    return userLoginRepositoryJPA.findUserLoginById(id).stream()
            .map(userLogin -> new UserLoginDTO(userLogin))
            .collect(Collectors.toList());
  }

  @Override
  public List<UserLoginDTO> findAllUserLogins() {
    return userLoginRepositoryJPA.findAll().stream()
            .map(userLogin -> new UserLoginDTO(userLogin))
            .collect(Collectors.toList());
  }


}
