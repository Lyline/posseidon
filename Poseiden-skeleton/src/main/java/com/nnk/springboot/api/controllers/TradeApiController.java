package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<List<Trade>> getAllTrade(){
    List<Trade>trades=service.getAll();
    if (trades.isEmpty()){
      logger.info("Read - No trade saved ");
      return new ResponseEntity<>(trades, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All trade : "+trades.size()+" trade(s)");
      return new ResponseEntity<>(trades, HttpStatus.OK);
    }
  }

  @PostMapping("/trades")
  public ResponseEntity<Trade>addTrade(@RequestBody Trade trade){
    if (trade.getAccount().isBlank() | trade.getType().isBlank() |
        trade.getBuyQuantity()==0){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Trade tradeSave=service.create(trade);
    logger.info("Create - trade : Account ="+trade.getAccount()+", Type ="
        + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity()+" is saved");
    return new ResponseEntity<>(tradeSave,HttpStatus.CREATED);
  }
}
