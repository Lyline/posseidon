package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public List<User> getAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public User create(User user) {
    return repository.save(user);
  }

  @Override
  @Transactional
  public Optional<User> findById(Integer id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public User update(Integer id, User user) {
    user.setId(id);
    return repository.save(user);
  }
}
