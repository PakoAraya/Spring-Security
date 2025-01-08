package com.segurito.spring.services;

import com.segurito.spring.dtos.UserLoginDTO;
import com.segurito.spring.models.UserLogin;
import com.segurito.spring.repositories.UserLoginRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

  private static final String ROLE_PREFIX = "ROLE_";

  @Autowired
  private UserLoginRepositoryJPA userLoginRepositoryJPA;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserLoginServiceImpl(UserLoginRepositoryJPA userLoginRepositoryJPA, PasswordEncoder passwordEncoder) {
    this.userLoginRepositoryJPA = userLoginRepositoryJPA;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserLogin userLogin = this.userLoginRepositoryJPA.findUserLoginByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

    GrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + userLogin.getRole());

    return new User(userLogin.getEmail(), userLogin.getPassword(), Collections.singletonList(authority));
  }

  public UserLoginDTO addUser(UserLoginDTO userLoginDTO) throws UserAlreadyExistsException {
    // Validating if the user already exists
    if (this.userLoginRepositoryJPA.findUserLoginByEmail(userLoginDTO.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists with email: " + userLoginDTO.getEmail());
    }

    // Encrypting password
    String encodedPassword = passwordEncoder.encode(userLoginDTO.getPassword());

    // Creating new UserLogin entity
    UserLogin newUser = new UserLogin();
    newUser.setName(userLoginDTO.getName());
    newUser.setEmail(userLoginDTO.getEmail());
    newUser.setPassword(encodedPassword);
    newUser.setRole(userLoginDTO.getRole());

    // Saving the user
    this.userLoginRepositoryJPA.save(newUser);

    // Returning DTO
    return new UserLoginDTO(newUser);
  }

  public Optional<UserLoginDTO> findUserLoginById(Long id) {
    return userLoginRepositoryJPA.findUserLoginById(id)
            .map(UserLoginDTO::new);
  }

  public List<UserLoginDTO> findAllUserLogins() {
    return userLoginRepositoryJPA.findAll().stream()
            .map(UserLoginDTO::new)
            .collect(Collectors.toList());
  }

  // Custom exception for user duplication
  public static class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
      super(message);
    }
  }
}