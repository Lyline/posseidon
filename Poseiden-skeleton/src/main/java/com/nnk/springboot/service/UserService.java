package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 The interface User service.
 */
@Service
public interface UserService {
  /**
   Gets all users.

   @return the list of users
   */
  List<User> getAll();

  /**
   Create user.

   @param user the user

   @return the user object created
   */
  User create(User user);

  /**
   Find user by id.

   @param id the id of the user

   @return the optional, return user object if it exists or optional empty
   */
  Optional<User> findById(Integer id);

  /**
   Update user by id.

   @param id   the id of the user
   @param user the user

   @return the user object updated
   */
  User update(Integer id, User user);

  /**
   Delete user by id.

   @param id the id of the user
   */
  void delete(Integer id);
}
