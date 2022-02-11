package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RuleNameController.class)
class RuleNameControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RuleNameServiceImpl service;

  private final RuleName ruleName= new RuleName();
  private final RuleName ruleName1= new RuleName();

  @BeforeEach
  void setUp() {
    ruleName.setId(1);
    ruleName.setName("Name_test");
    ruleName.setDescription("Description_test");
    ruleName.setJson("Json_test");
    ruleName.setTemplate("Template_test");
    ruleName.setSqlStr("Sql_String_test");
    ruleName.setSqlPart("Sql_Part_test");

    ruleName1.setId(2);
    ruleName1.setName("Name_test1");
    ruleName1.setDescription("Description_test1");
    ruleName1.setJson("Json_test1");
    ruleName1.setTemplate("Template_test1");
    ruleName1.setSqlStr("Sql_String_test1");
    ruleName1.setSqlPart("Sql_Part_test1");
  }

  @Test
  void givenTwoRuleWhenRuleHomeThenRuleHomeTableWithTwoElementsDisplayed() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(ruleName,ruleName1));

    //When
    mockMvc.perform(get("/ruleName/list"))
        .andExpect(view().name("ruleName/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Name_test")))
        .andExpect(content().string(containsString("Description_test")))
        .andExpect(content().string(containsString("Json_test")))
        .andExpect(content().string(containsString("Template_test")))
        .andExpect(content().string(containsString("Sql_String_test")))
        .andExpect(content().string(containsString("Sql_Part_test")))

        .andExpect(content().string(containsString("2")))
        .andExpect(content().string(containsString("Name_test1")))
        .andExpect(content().string(containsString("Description_test1")))
        .andExpect(content().string(containsString("Json_test1")))
        .andExpect(content().string(containsString("Template_test1")))
        .andExpect(content().string(containsString("Sql_String_test1")))
        .andExpect(content().string(containsString("Sql_Part_test1")));
  }

  @Test
  void showRuleNameForm() throws Exception {
    //When
    mockMvc.perform(get("/ruleName/add"))
        .andExpect(view().name("ruleName/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Name")))
        .andExpect(content().string(containsString("Description")))
        .andExpect(content().string(containsString("Json")))
        .andExpect(content().string(containsString("Template")))
        .andExpect(content().string(containsString("SQL String")))
        .andExpect(content().string(containsString("SQL Part")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Add Rule Name")));
  }

  @Test
  void givenANewRatingValidWhenCreateThenRatingIsSavedAndRatingHomeDisplayed() throws Exception {
    //Given
    when(service.create(any())).thenReturn(ruleName);
    when(service.getAll()).thenReturn(List.of(ruleName));

    //When
    mockMvc.perform(post("/ruleName/validate")
            .param("name","Name_test")
            .param("description","description_test")
            .param("json","Json_test")
            .param("template","Template_test")
            .param("sqlStr","Sql_String_test")
            .param("sqlPart","Sql_Part_test"))
        .andExpect(view().name("ruleName/list"))
        .andExpect(status().isCreated())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Name_test")))
        .andExpect(content().string(containsString("Description_test")))
        .andExpect(content().string(containsString("Json_test")))
        .andExpect(content().string(containsString("Template_test")))
        .andExpect(content().string(containsString("Sql_String_test")))
        .andExpect(content().string(containsString("Sql_Part_test")));
  }

  @Test
  void givenANewRuleNotValidWhenCreateThenRuleFormDisplayedWithErrorMessage() throws Exception {
    //When
    mockMvc.perform(post("/ruleName/validate"))
        .andExpect(view().name("ruleName/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Name must not be empty")))
        .andExpect(content().string(containsString("Description must not be empty")))
        .andExpect(content().string(containsString("Json must not be empty")))
        .andExpect(content().string(containsString("Template must not be empty")))
        .andExpect(content().string(containsString("SQL String must not be empty")))
        .andExpect(content().string(containsString("SQL Part must not be empty")));
  }

  @Test
  void showUpdateForm() throws Exception {
    //Given
    when(service.getById(anyInt())).thenReturn(ruleName);

    //When
    mockMvc.perform(get("/ruleName/update/1")
        .param("name","Name_test")
        .param("description","description_test")
        .param("json","Json_test")
        .param("template","Template_test")
        .param("sqlStr","Sql_String_test")
        .param("sqlPart","Sql_Part_test"))
        .andExpect(view().name("ruleName/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Name")))
        .andExpect(content().string(containsString("Name_test")))

        .andExpect(content().string(containsString("Description")))
        .andExpect(content().string(containsString("Description_test")))

        .andExpect(content().string(containsString("Json")))
        .andExpect(content().string(containsString("Json_test")))

        .andExpect(content().string(containsString("Template")))
        .andExpect(content().string(containsString("Template_test")))

        .andExpect(content().string(containsString("SQL String")))
        .andExpect(content().string(containsString("Sql_String_test")))

        .andExpect(content().string(containsString("SQL Part")))
        .andExpect(content().string(containsString("Sql_Part_test")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Update Rule Name")));
  }

  @Test
  void givenAExistingRuleWhenValidUpdateThenRuleIsUpdatedAndRuleHomeDisplayed() throws Exception {
    //Given
    when(service.update(anyInt(),any())).thenReturn(ruleName);
    when(service.getAll()).thenReturn(List.of(ruleName));

    //When
    mockMvc.perform(post("/ruleName/update/1")
            .param("name","Name_test")
            .param("description","description_test")
            .param("json","Json_test")
            .param("template","Template_test")
            .param("sqlStr","Sql_String_test")
            .param("sqlPart","Sql_Part_test"))
        .andExpect(view().name("ruleName/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Name_test")))
        .andExpect(content().string(containsString("Description_test")))
        .andExpect(content().string(containsString("Json_test")))
        .andExpect(content().string(containsString("Template_test")))
        .andExpect(content().string(containsString("Sql_String_test")))
        .andExpect(content().string(containsString("Sql_Part_test")));
  }

  @Test
  void givenAExistingRuleWhenNotValidUpdateThenRuleUpdateFormDisplayedWithErrorMessages() throws Exception {
    //When
    mockMvc.perform(post("/ruleName/update/1"))
        .andExpect(view().name("ruleName/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Name must not be empty")))
        .andExpect(content().string(containsString("Description must not be empty")))
        .andExpect(content().string(containsString("Json must not be empty")))
        .andExpect(content().string(containsString("Template must not be empty")))
        .andExpect(content().string(containsString("SQL String must not be empty")))
        .andExpect(content().string(containsString("SQL Part must not be empty")));
  }

  @Test
  void givenARuleWhenDeleteThenRuleIsDeletedAndRuleHomeDisplayed() throws Exception {
    //When
    mockMvc.perform(get("/ruleName/delete/1"))
        .andExpect(view().name("ruleName/list"))
        .andExpect(status().isOk());
  }
}