package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 The interface Rule name service.
 */
@Service
public interface RuleNameService {
  /**
   Gets all rule names.

   @return the list of rule names
   */
  List<RuleName> getAll();

  /**
   Create rule name.

   @param ruleName the rule name

   @return the rule name object created
   */
  RuleName create(RuleName ruleName);

  /**
   Gets rule name by id.

   @param id the id of the rule name

   @return the rule name if it exists or null
   */
  RuleName getById(Integer id);

  /**
   Find rule name by id.

   @param id the id of the rule name

   @return the optional, return the rule name object if it exists or optional empty
   */
  Optional<RuleName> findById(Integer id);

  /**
   Update rule name by id.

   @param id               the id of the rule name
   @param ruleNameToUpdate the rule name to update

   @return the rule name object updated
   */
  RuleName update(Integer id, RuleName ruleNameToUpdate);

  /**
   Delete rule name by id.

   @param id the id of the rule name
   */
  void delete(Integer id);
}
