package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameServiceImpl;
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

@WebMvcTest(RuleNameApiController.class)
class RuleNameApiControllerTest {
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
  void givenTwoRuleNamesSavedWhenGetAllThenRuleNameListWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(ruleName,ruleName1));

    //When
    mockMvc.perform(get("/api/ruleNames")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].id",is(1)))
        .andExpect(jsonPath("$[0].name",is("Name_test")))
        .andExpect(jsonPath("$[0].description",is("Description_test")))
        .andExpect(jsonPath("$[0].json",is("Json_test")))
        .andExpect(jsonPath("$[0].template",is("Template_test")))
        .andExpect(jsonPath("$[0].sqlStr",is("Sql_String_test")))
        .andExpect(jsonPath("$[0].sqlPart",is("Sql_Part_test")))


        .andExpect(jsonPath("$[1].id",is(2)))
        .andExpect(jsonPath("$[1].name",is("Name_test1")))
        .andExpect(jsonPath("$[1].description",is("Description_test1")))
        .andExpect(jsonPath("$[1].json",is("Json_test1")))
        .andExpect(jsonPath("$[1].template",is("Template_test1")))
        .andExpect(jsonPath("$[1].sqlStr",is("Sql_String_test1")))
        .andExpect(jsonPath("$[1].sqlPart",is("Sql_Part_test1")));
  }

  @Test
  void givenNoRuleNameSavedWhenGetAllThenRuleNameListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/ruleNames")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }
}