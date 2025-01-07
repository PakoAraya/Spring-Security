package com.segurito.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/home")
  public String home(){
    return "home";
  }

  @GetMapping("/problems")
  public String problems(){
    return "problems";
  }
}
