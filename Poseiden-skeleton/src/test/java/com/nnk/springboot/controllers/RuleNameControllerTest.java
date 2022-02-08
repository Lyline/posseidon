package com.nnk.springboot.controllers;

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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}