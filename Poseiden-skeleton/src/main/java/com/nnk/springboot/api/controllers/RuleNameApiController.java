package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 The type Rule name api controller.
 */
@RestController
@RequestMapping("/api")
public class RuleNameApiController extends HandlerException {

  @Autowired
  private RuleNameService service;

  private final Logger logger= LoggerFactory.getLogger(RuleNameApiController.class);

  /**
   Instantiates a new Rule name api controller.

   @param service the service
   */
  public RuleNameApiController(RuleNameService service) {
    this.service = service;
  }

  /**
   Get all rule names.

   @return the response entity status 200 with the list of rule name, or status 204 with an empty list
   */
  @GetMapping("/ruleNames")
  public ResponseEntity<List<RuleName>> getAllRuleNames(){
    List<RuleName>ruleNames=service.getAll();
    if (ruleNames.isEmpty()){
      logger.info("Read - No rule name saved ");
      return new ResponseEntity<>(ruleNames, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All rule name : "+ruleNames.size()+" rule name(s)");
      return new ResponseEntity<>(ruleNames, HttpStatus.OK);
    }
  }

  /**
   Add rule name.

   @param ruleName the rule name

   @return the response entity status 201 with the rule name object saved
   */
  @PostMapping("/ruleNames")
  public ResponseEntity<RuleName>addRuleName(@Valid @RequestBody RuleName ruleName){
    RuleName ruleNameSave=service.create(ruleName);
    logger.info("Create - rule name : Name ="+ruleName.getName()+", Description ="
        + ruleName.getDescription()+" is saved");
    return new ResponseEntity<>(ruleNameSave,HttpStatus.CREATED);
  }

  /**
   Update rule name.

   @param id       the id of the rule name to update
   @param ruleName the rule name submitted

   @return the response entity status 201 with the rule name object updated, or status 404 if the rule name is not exist
   */
  @PutMapping("/ruleNames/{id}")
  public ResponseEntity<RuleName>updateRuleName(@PathVariable(value = "id") Integer id,
                                                @Valid @RequestBody RuleName ruleName){
    Optional<RuleName> ruleNameIsExist=service.findById(id);
    if (ruleNameIsExist.isEmpty()){
      logger.info("Read - rule name with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    RuleName curveUpdate=service.update(id,ruleName);
    logger.info("Update - rule name : Name ="+ruleName.getName()+", Description ="
        + ruleName.getDescription()+" is saved");
    return new ResponseEntity<>(curveUpdate,HttpStatus.CREATED);
  }

  /**
   Delete rule name.

   @param id the id of rule name to delete

   @return the response entity status 200 when the rule name is deleted, or status 404 if the rule name is not exist
   */
  @DeleteMapping("/ruleNames/{id}")
  public ResponseEntity<RuleName>deleteRuleName(@PathVariable(value = "id") Integer id){
    Optional<RuleName>ruleNameIsExist=service.findById(id);
    if (ruleNameIsExist.isEmpty()){
      logger.info("Read - rule name with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    service.delete(id);
    logger.info("Delete - rule name with id "+id+" is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
