package com.nnk.springboot.api.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 The User DTO use in the API application.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
  private Integer id;
  private String username;
  private String fullName;
  private String role;
}
