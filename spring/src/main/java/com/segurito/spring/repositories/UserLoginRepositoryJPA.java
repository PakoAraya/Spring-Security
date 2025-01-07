package com.segurito.spring.repositories;

import com.segurito.spring.models.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserLoginRepositoryJPA extends JpaRepository<UserLogin, Long> {
  List<UserLogin> findUserLoginById(Long id);
}
