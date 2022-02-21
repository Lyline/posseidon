package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository repository;

  private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

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
    String password= user.getPassword();
    String encodePassword= passwordEncoder.encode(password);
    user.setPassword(encodePassword);
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
    String password= user.getPassword();
    String encodePassword= passwordEncoder.encode(password);
    user.setPassword(encodePassword);
    return repository.save(user);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional= repository.findByUsername(username);

    org.springframework.security.core.userdetails.User springUser=null;

    if (userOptional.isEmpty()){
      throw new UsernameNotFoundException(username+" not found");
    }else{
      User user= userOptional.get();

      List<String> roles= Collections.singletonList(user.getRole());
      Set<GrantedAuthority> grantedAuthorities= new HashSet<>();
      for (String role:roles){
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
      }

      springUser= new org.springframework.security.core.userdetails.User(
          username,user.getPassword(), grantedAuthorities);

    }

    return springUser;
  }
}
