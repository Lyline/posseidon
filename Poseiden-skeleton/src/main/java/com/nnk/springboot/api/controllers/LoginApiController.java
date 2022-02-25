package com.nnk.springboot.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 The type Login api controller.
 */
@RestController
public class LoginApiController {

  /**
   Get home api.

   @return Welcome message
   */
  @RequestMapping("/api")
  public String getHomeApi(){
    return "Welcome to API";
  }
}
