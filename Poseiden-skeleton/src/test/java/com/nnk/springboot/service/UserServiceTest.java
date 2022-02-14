package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

  private final UserRepository repository= mock(UserRepository.class);
  private final UserService classUnderTest= new UserServiceImpl(repository);

  private final User user= new User();
  private final User user1= new User();
  private final User userToSave= new User();

  @BeforeEach
  void setUp() {
    user.setId(1);
    user.setUsername("Username_Test");
    user.setPassword("Password");
    user.setFullName("FullName_Test");
    user.setRole("Role_Test");

    user1.setId(2);
    user1.setUsername("Username_Test1");
    user1.setPassword("Password1");
    user1.setFullName("FullName_Test1");
    user1.setRole("Role_Test1");

    userToSave.setUsername("Username_Test1");
    userToSave.setPassword("Password1");
    userToSave.setFullName("FullName_Test1");
    userToSave.setRole("Role_Test1");
  }

  @Test
  void givenTwoUsersWhenGetAllThenReturnListOfUserTwoUsers() {
    //Given
    when(repository.findAll()).thenReturn(List.of(user,user1));

    //When
    List<User>actual= classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void givenNoUserWhenGetAllThenReturnAnEmptyList() {
    //Given
    when(repository.findAll()).thenReturn(List.of());

    //When
    List<User>actual= classUnderTest.getAll();

    //Then
    assertTrue(actual.isEmpty());
  }
}