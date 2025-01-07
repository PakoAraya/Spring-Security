package com.segurito.spring.interfaces;

import com.segurito.spring.dtos.UserLoginDTO;

import java.util.List;
import java.util.Optional;

public interface UserLogin {
  List<UserLoginDTO> findUserLoginById(Long id);
  List<UserLoginDTO> findAllUserLogins();
  //Optional<UserLogin> findUserLoginById(Long id);
}