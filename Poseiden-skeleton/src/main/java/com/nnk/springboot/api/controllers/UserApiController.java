package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.dto.UserDTO;
import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 The type User api controller.
 */
@RestController
@RequestMapping("/api")
public class UserApiController extends HandlerException {

  @Autowired
  private UserService service;

  private final Logger logger= LoggerFactory.getLogger(UserService.class);
  private final ModelMapper mapper= new ModelMapper();


  /**
   Instantiates a new User api controller.

   @param service the service
   */
  public UserApiController(UserService service) {
    this.service = service;
  }

  /**
   Get all users.

   @return the response entity status 200 with the list of users, or status 204 with an empty list
   */
  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>>getAllUsers(){
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

  /**
   Add user.

   @param user the user

   @return the response entity status 201 with the user object saved
   */
  @PostMapping("/users")
  public ResponseEntity<User>addUser(@Valid @RequestBody User user){
   User userSave=service.create(user);
    logger.info("Create - user : Username ="+user.getUsername()+", Full name ="
        + user.getFullName()+", Role ="+ user.getRole()+" is saved");
    return new ResponseEntity<>(userSave,HttpStatus.CREATED);
  }

  /**
   Update user.

   @param id   the id of user to update
   @param user the user submitted

   @return the response entity status 201 with the user object updated, or status 404 if the user is not exist
   */
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

  /**
   Delete user.

   @param id the id of the user to deleted

   @return the response entity status 200 when the user is deleted, or status 404 if the user is not exist
   */
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
