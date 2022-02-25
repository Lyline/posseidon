package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 The type Trade api controller.
 */
@RestController
@RequestMapping("/api")
public class TradeApiController extends HandlerException {

  private final TradeServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(TradeApiController.class);

  /**
   Instantiates a new Trade api controller.

   @param service the service
   */
  public TradeApiController(TradeServiceImpl service) {
    this.service = service;
  }

  /**
   Get all trade.

   @return the response entity status 200 with the list of trades, or status 204 with an empty list
   */
  @GetMapping("/trades")
  public ResponseEntity<List<Trade>> getAllTrades(){
    List<Trade>trades=service.getAll();
    if (trades.isEmpty()){
      logger.info("Read - No trade saved ");
      return new ResponseEntity<>(trades, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All trade : "+trades.size()+" trade(s)");
      return new ResponseEntity<>(trades, HttpStatus.OK);
    }
  }

  /**
   Add trade.

   @param trade the trade

   @return the response entity status 201 with the trade object saved
   */
  @PostMapping("/trades")
  public ResponseEntity<Trade>addTrade(@Valid @RequestBody Trade trade){
    Trade tradeSave=service.create(trade);
    logger.info("Create - trade : Account ="+trade.getAccount()+", Type ="
        + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity()+" is saved");
    return new ResponseEntity<>(tradeSave,HttpStatus.CREATED);
  }

  /**
   Update trade.

   @param id    the id of trade to update
   @param trade the trade submitted

   @return the response entity status 201 with the trade object updated, or status 404 if the trade is not exist
   */
  @PutMapping("/trades/{id}")
  public ResponseEntity<Trade>updateTrade(@PathVariable(value = "id") Integer id,
                                          @Valid @RequestBody Trade trade){
    Optional<Trade> ruleNameIsExist=service.findById(id);
    if (ruleNameIsExist.isEmpty()){
      logger.info("Read - trade with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Trade tradeUpdate =service.update(id,trade);
    logger.info("Update - trade : Account ="+trade.getAccount()+", Type ="
        + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity()+" is saved");
    return new ResponseEntity<>(tradeUpdate,HttpStatus.CREATED);
  }

  /**
   Delete trade.

   @param id the id of the trade to delete

   @return the response entity status 200 when the trade is deleted, or status 404 if the trade is not exist
   */
  @DeleteMapping("/trades/{id}")
  public ResponseEntity<Trade>deleteTrade(@PathVariable(value = "id") Integer id){
    Optional<Trade>tradeIsExist=service.findById(id);
    if (tradeIsExist.isEmpty()){
      logger.info("Read - trade with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    service.delete(id);
    logger.info("Delete - trade with id "+id+" is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
