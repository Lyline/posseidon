package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RuleNameService {
  List<RuleName> getAll();
  RuleName create(RuleName ruleName);
  RuleName getById(Integer id);
  Optional<RuleName> findById(Integer id);
  RuleName update(Integer id, RuleName ruleNameToUpdate);
  void delete(Integer id);
}
