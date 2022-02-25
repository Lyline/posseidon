package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
  /**
   Find by username.

   @param username the username

   @return the optional result, return user object if it is exist, or optionnal is empty, if the user is not exist
   */
  Optional<User> findByUsername(String username);
}
