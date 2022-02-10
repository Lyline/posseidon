package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RuleNameServiceImpl implements RuleNameService{
  private final RuleNameRepository repository;

  public RuleNameServiceImpl(RuleNameRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public List<RuleName> getAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public RuleName create(RuleName ruleName) {
    return repository.save(ruleName);
  }

  @Override
  @Transactional
  public RuleName getById(Integer id) {
    return repository.getById(id);
  }

  @Override
  @Transactional
  public RuleName update(Integer id, RuleName ruleNameToUpdate) {
    ruleNameToUpdate.setId(id);
    return repository.save(ruleNameToUpdate);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}