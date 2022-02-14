package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
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
public class TradeApiController {

  private final TradeServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(TradeApiController.class);

  public TradeApiController(TradeServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/trades")
  public ResponseEntity<List<Trade>> getAllRuleName(){
    List<Trade>trades=service.getAll();
    if (trades.isEmpty()){
      logger.info("Read - No trade saved ");
      return new ResponseEntity<>(trades, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All trade : "+trades.size()+" trade(s)");
      return new ResponseEntity<>(trades, HttpStatus.OK);
    }
  }
}
