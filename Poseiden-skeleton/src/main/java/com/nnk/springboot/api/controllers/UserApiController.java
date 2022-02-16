package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.dto.UserDTO;
import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserApiController extends HandlerException {

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
  public ResponseEntity<User>addUser(@Valid @RequestBody User user){
   User userSave=service.create(user);
    logger.info("Create - user : Username ="+user.getUsername()+", Full name ="
        + user.getFullName()+", Role ="+ user.getRole()+" is saved");
    return new ResponseEntity<>(userSave,HttpStatus.CREATED);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<User>updateUser(@PathVariable(value = "id") Integer id,
                                        @Valid @RequestBody User user){
    Optional<User> userIsExist=service.findById(id);
    if (userIsExist.isEmpty()){
      logger.info("Read - user with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    User userUpdate =service.update(id,user);
    logger.info("Update - user : Username ="+user.getUsername()+", Full name ="
        + user.getFullName()+", Role ="+ user.getRole()+" is saved");
    return new ResponseEntity<>(userUpdate,HttpStatus.CREATED);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<User>deleteUser(@PathVariable(value = "id") Integer id){
    Optional<User>userIsExist=service.findById(id);
    if (userIsExist.isEmpty()){
      logger.info("Read - user with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    service.delete(id);
    logger.info("Delete - user with id "+id+" is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
