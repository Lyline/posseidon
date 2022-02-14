package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserServiceImpl service;

  private final User user= new User();
  private final User user1= new User();

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
  }

  @Test
  void givenTwoUserSavedWhenGetAllThenUserListWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(user,user1));

    //When
    mockMvc.perform(get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].id",is(1)))
        .andExpect(jsonPath("$[0].username",is("Username_Test")))
        .andExpect(jsonPath("$[0].fullName",is("FullName_Test")))
        .andExpect(jsonPath("$[0].role",is("Role_Test")))

        .andExpect(jsonPath("$[1].id",is(2)))
        .andExpect(jsonPath("$[1].username",is("Username_Test1")))
        .andExpect(jsonPath("$[1].fullName",is("FullName_Test1")))
        .andExpect(jsonPath("$[1].role",is("Role_Test1")));
  }

  @Test
  void givenNoUserSavedWhenGetAllThenUserListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }
}