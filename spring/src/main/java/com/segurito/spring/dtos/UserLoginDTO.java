package com.segurito.spring.dtos;

import com.segurito.spring.models.UserLogin;

public class UserLoginDTO {
  private Long id;
  private String name;
  private String email;
  private String password;
  private String role;

  public UserLoginDTO() {
  }

  public UserLoginDTO(Long id, String name, String email, String password, String role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public UserLoginDTO(UserLogin userLogin) {
    this.id = userLogin.getId();
    this.name = userLogin.getName();
    this.email = userLogin.getEmail();
    this.password = userLogin.getPassword();
    this.role = userLogin.getRole();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}