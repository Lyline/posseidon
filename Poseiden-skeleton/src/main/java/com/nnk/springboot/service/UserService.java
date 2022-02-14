package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
  List<User> getAll();
  User create(User user);
  Optional<User> findById(Integer id);
  User update(Integer id, User user);
  void delete(Integer id);
}
