package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RuleNameApiController {

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
}
