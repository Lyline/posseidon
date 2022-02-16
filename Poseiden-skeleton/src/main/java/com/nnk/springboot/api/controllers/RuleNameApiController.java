package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RuleNameApiController extends HandlerException {

  private final RuleNameServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(RuleNameApiController.class);

  public RuleNameApiController(RuleNameServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/ruleNames")
  public ResponseEntity<List<RuleName>> getAllRuleName(){
    List<RuleName>ruleNames=service.getAll();
    if (ruleNames.isEmpty()){
      logger.info("Read - No rule name saved ");
      return new ResponseEntity<>(ruleNames, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All rule name : "+ruleNames.size()+" rule name(s)");
      return new ResponseEntity<>(ruleNames, HttpStatus.OK);
    }
  }

  @PostMapping("/ruleNames")
  public ResponseEntity<RuleName>addRuleName(@Valid @RequestBody RuleName ruleName){
    RuleName ruleNameSave=service.create(ruleName);
    logger.info("Create - rule name : Name ="+ruleName.getName()+", Description ="
        + ruleName.getDescription()+" is saved");
    return new ResponseEntity<>(ruleNameSave,HttpStatus.CREATED);
  }

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
