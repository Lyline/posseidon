package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  List<User> getAll();
  User create(User user);
}
