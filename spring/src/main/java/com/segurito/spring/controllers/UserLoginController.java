package com.segurito.spring.controllers;

import com.segurito.spring.dtos.UserLoginDTO;
import com.segurito.spring.services.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class UserLoginController {

  @Autowired
  private UserLoginServiceImpl userLoginServiceImpl;

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  @GetMapping("/home")
  public String home(){
    return "home";
  }

  @GetMapping("/problems")
  public String problems(){
    return "problems";
  }

  @PreAuthorize(" hasRole('ADMIN')") //This annotation is used to restrict access to the dashboard only to users with the role of ADMIN
  @GetMapping("/dashboard")
  public String dashboard(Model model){
    List<UserLoginDTO> users = userLoginServiceImpl.findAllUserLogins();
    model.addAttribute("users", users);
    return "dashboard";
  }

  @PostMapping("/user/add")
  public UserLoginDTO add(@Valid @RequestBody UserLoginDTO userLoginDTO){
    try{
      return userLoginServiceImpl.addUser(userLoginDTO);
    }catch(IllegalArgumentException e) {
      return new UserLoginDTO();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/user/{id}")
  public UserLoginDTO getUserById(@PathVariable Long id) {
    return userLoginServiceImpl.findUserLoginById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }

}
