package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService service;

  private final User user= new User();
  private final User user1= new User();

  @BeforeEach
  void setUp() {
    user.setId(1);
    user.setUsername("Username_Test");
    user.setPassword("Password_Test");
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

  @Test
  void givenANewValidUserWhenCreateThenUserIsSavedAndStatus201() throws Exception {
    //Given
    when(service.create(any())).thenReturn(user);

    //When
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"username\":\"Username_Test\","+
                "\"password\":\"Password_Test\","+
                "\"fullName\":\"FullName_Test\","+
                "\"role\":\"Role_Test\""+
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.username",is("Username_Test")))
        .andExpect(jsonPath("$.password",is("Password_Test")))
        .andExpect(jsonPath("$.fullName",is("FullName_Test")))
        .andExpect(jsonPath("$.role",is("Role_Test")));
  }

  @Test
  void givenANewNotValidUserWhenCreateThenUserIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"username\":\"\","+
                "\"password\":\"\","+
                "\"fullName\":\"\","+
                "\"role\":\"\""+
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenAExistingUserWhenUpdateThenUserIsSavedAndStatus201() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(user));
    when(service.update(anyInt(),any())).thenReturn(user);

    //When
    mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"username\":\"Username_Test\","+
                "\"password\":\"Password_Test\","+
                "\"fullName\":\"FullName_Test\","+
                "\"role\":\"Role_Test\""+
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.username",is("Username_Test")))
        .andExpect(jsonPath("$.password",is("Password_Test")))
        .andExpect(jsonPath("$.fullName",is("FullName_Test")))
        .andExpect(jsonPath("$.role",is("Role_Test")));
  }

  @Test
  void givenANotValidUserWhenUpdateThenUserIsNotSavedAndStatus400() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(user));
    //When
    mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"username\":\"\","+
                "\"password\":\"\","+
                "\"fullName\":\"\","+
                "\"role\":\"\""+
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenANotExistUserWhenUpdateThenUserIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());

    //When
    mockMvc.perform(put("/api/users/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"username\":\"Username_Test\","+
                "\"password\":\"Password_Test\","+
                "\"fullName\":\"FullName_Test\","+
                "\"role\":\"Role_Test\""+
                "}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenAExistUserWhenDeleteThenUserIsDeletedAndStatus200() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(user));
    //When
    mockMvc.perform(delete("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void givenANotExistUserWhenDeleteThenUserIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());
    //When
    mockMvc.perform(delete("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}