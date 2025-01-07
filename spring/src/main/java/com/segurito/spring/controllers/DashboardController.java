package com.segurito.spring.controllers;

import com.segurito.spring.dtos.UserLoginDTO;
import com.segurito.spring.services.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

  @Autowired
  private UserLoginServiceImpl userLoginServiceImpl;

  @GetMapping("/dashboard")
  public String dashboard(Model model){
    List<UserLoginDTO> users = userLoginServiceImpl.findAllUserLogins();
    model.addAttribute("users", users);
    return "dashboard";
  }
}
