package com.nnk.springboot.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginApiController {

 /* private final OAuth2AuthorizedClientService clientService;

  public LoginApiController(OAuth2AuthorizedClientService clientService) {
    this.clientService = clientService;
  }*/


  @RequestMapping("/api")
  public String getHomeApi(){
    return "Welcome to API";
  }
}
