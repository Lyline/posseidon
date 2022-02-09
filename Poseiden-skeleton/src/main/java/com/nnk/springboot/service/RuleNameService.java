package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RuleNameService {
  List<RuleName> getAll();
  RuleName create(RuleName ruleName);
  RuleName getById(Integer id);
  RuleName update(Integer id, RuleName ruleNameToUpdate);
}
