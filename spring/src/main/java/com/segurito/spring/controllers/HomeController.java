package com.segurito.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home(){
    return "home";
  }

  @GetMapping("/dashboard")
  public String dashboard(){
    return "dashboard";
  }

  @GetMapping("/problems")
  public String problems(){
    return "problems";
  }
}
