package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RuleNameServiceTest {
  private final RuleNameRepository repository= mock(RuleNameRepository.class);
  private final RuleNameServiceImpl classUnderTest= new RuleNameServiceImpl(repository);

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
  void getAll() {
    //Given
    when(repository.findAll()).thenReturn(List.of(ruleName,ruleName1));

    //When
    List<RuleName>actual= classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
  }
}