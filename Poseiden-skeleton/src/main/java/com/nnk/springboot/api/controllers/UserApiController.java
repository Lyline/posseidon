package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.dto.UserDTO;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserApiController {

  private final UserServiceImpl service;

  private final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
  private final ModelMapper mapper= new ModelMapper();



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
      logger.info("Read - No user saved");
      return new ResponseEntity<>(usersDto,HttpStatus.NO_CONTENT);
    }

    logger.info("Read - All users : "+usersDto.size()+" user(s)");
    return new ResponseEntity<>(usersDto, HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<User>addUser(@RequestBody User user){
    if (user.getUsername().isBlank() | user.getPassword().isBlank() |
        user.getFullName().isBlank() | user.getRole().isBlank()){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    User userSave=service.create(user);
    logger.info("Create - user : Username ="+user.getUsername()+", Full name ="
        + user.getFullName()+", Role ="+ user.getRole()+" is saved");
    return new ResponseEntity<>(userSave,HttpStatus.CREATED);
  }
}
