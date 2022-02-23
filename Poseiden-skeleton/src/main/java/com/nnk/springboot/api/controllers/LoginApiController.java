package com.nnk.springboot.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginApiController {


  @RequestMapping("/api")
  public String getHomeApi(){
    return "Welcome to API";
  }
}
