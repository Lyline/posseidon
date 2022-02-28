package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserWebControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService service;

  User user= new User();
  User user1= new User();

  @BeforeEach
  void setUp() {
    user.setUsername("userTest");
    user.setFullName("UserFullName");
    user.setPassword("user123A!");
    user.setRole("USER");

    user1.setUsername("adminTest");
    user1.setFullName("AdminFullName");
    user1.setPassword("admin123A!");
    user1.setRole("ADMIN");

  }

  @Test
  void givenTwoUsersWhenUserHomeThenUserHomeTableWithTwoElementsDisplayed() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(user,user1));
    //When
    mockMvc.perform(get("/user/list"))
        .andExpect(view().name("user/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("userTest")))
        .andExpect(content().string(containsString("UserFullName")))
        .andExpect(content().string(containsString("USER")))

        .andExpect(content().string(containsString("2")))
        .andExpect(content().string(containsString("adminTest")))
        .andExpect(content().string(containsString("AdminFullName")))
        .andExpect(content().string(containsString("ADMIN")));
  }

  @Test
  void showAddUserForm() throws Exception {
    //When
    mockMvc.perform(get("/user/add"))
        .andExpect(view().name("user/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Full Name")))
        .andExpect(content().string(containsString("User Name")))
        .andExpect(content().string(containsString("Password")))
        .andExpect(content().string(containsString("Role")))
        .andExpect(content().string(containsString("ADMIN")))
        .andExpect(content().string(containsString("USER")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Add user")));
  }

  @Test
  void givenANewUserValidWhenCreateThenUserIsSavedAndUserHomeDisplayed() throws Exception {
    //Given
    when(service.create(any())).thenReturn(user);
    when(service.getAll()).thenReturn(List.of(user));

    //When
    mockMvc.perform(post("/user/validate")
            .param("fullName","userFullName")
            .param("username","userTest")
            .param("password","user123A!")
            .param("role","USER"))
        .andExpect(view().name("redirect:/user/list"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void showUpdateUserForm() throws Exception {
    //When
    when(service.findById(anyInt())).thenReturn(Optional.ofNullable(user));

    mockMvc.perform(get("/user/update/1"))
        .andExpect(view().name("user/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("userTest")))
        .andExpect(content().string(containsString("UserFullName")))
        .andExpect(content().string(containsString("USER")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Update User")));
  }

  @Test
  void givenAUserWhenDeleteThenUserIsDeletedAndTradeHomeDisplayed() throws Exception {
    //When
    when(service.findById(anyInt())).thenReturn(Optional.ofNullable(user));

    mockMvc.perform(get("/user/delete/1"))
        .andExpect(view().name("redirect:/user/list"))
        .andExpect(status().is3xxRedirection());
  }
}