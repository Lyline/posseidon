package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
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
public class BidListApiController {

  private final BidListServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(BidListApiController.class);

  public BidListApiController(BidListServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/bidList")
  public ResponseEntity<List<BidList>> getAllBidList(){
    List<BidList>bids= service.getAll();
    if (bids.isEmpty()){
      logger.info("Read - No bid list saved ");
      return new ResponseEntity<>(bids, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All bid list : "+bids.size()+" bid(s)");
      return new ResponseEntity<>(bids, HttpStatus.OK);
    }
  }
}
