package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.dto.UserDTO;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserApiController {

  private final UserServiceImpl service;
  private ModelMapper mapper= new ModelMapper();


  public UserApiController(UserServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>>getAllUser(){
    List<User>users= service.getAll();

    List<UserDTO>usersDto= users.stream()
        .map(user-> mapper.map(user,UserDTO.class))
        .collect(Collectors.toList());

    if (usersDto.isEmpty()){
      return new ResponseEntity<>(usersDto,HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(usersDto, HttpStatus.OK);
  }


}
