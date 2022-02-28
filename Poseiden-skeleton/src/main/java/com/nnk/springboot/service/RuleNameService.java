package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 The type Rule name service implementation.
 */
@Service
public class RuleNameService {
  private final RuleNameRepository repository;

  /**
   Instantiates a new Rule name service.

   @param repository the repository
   */
  public RuleNameService(RuleNameRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public List<RuleName> getAll() {
    return repository.findAll();
  }

  @Transactional
  public RuleName create(RuleName ruleName) {
    return repository.save(ruleName);
  }

  @Transactional
  public RuleName getById(Integer id) {
    return repository.getById(id);
  }

  @Transactional
  public Optional<RuleName> findById(Integer id) {
    return repository.findById(id);
  }

  @Transactional
  public RuleName update(Integer id, RuleName ruleNameToUpdate) {
    ruleNameToUpdate.setId(id);
    return repository.save(ruleNameToUpdate);
  }

  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
