package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 The type Bid list api controller.
 */
@RestController
@RequestMapping("/api")
public class BidListApiController extends HandlerException{

  private final BidListServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(BidListApiController.class);

  /**
   Instantiates a new Bid list api controller.

   @param service the service
   */
  public BidListApiController(BidListServiceImpl service) {
    this.service = service;
  }

  /**
   Get all bids.

   @return the response entity status 200 with the list of bid, or status 204 with an empty list
   */
  @GetMapping("/bids")
  public ResponseEntity<List<BidList>> getAllBids(){
    List<BidList>bids= service.getAll();
    if (bids.isEmpty()){
      logger.info("Read - No bid list saved ");
      return new ResponseEntity<>(bids, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All bid list : "+bids.size()+" bid(s)");
      return new ResponseEntity<>(bids, HttpStatus.OK);
    }
  }

  /**
   Add bid.

   @param bid the bid

   @return the response entity status 201 with bid object saved
   */
  @PostMapping("/bids")
  public ResponseEntity<BidList> addBidList(@Valid @RequestBody BidList bid){
    BidList bidSave=service.create(bid);

    logger.info("Create - bid list : Account ="+bid.getAccount()+", Type ="
        +bid.getType()+", Bid quantity ="+bid.getBidQuantity()+" is saved");
    return new ResponseEntity<>(bidSave,HttpStatus.CREATED);
  }

  /**
   Update bid.

   @param id  the id of bid to update
   @param bid the bid submitted

   @return the response entity status 201 with the bid object updated, or status 404 if the bid to update is not exist
   */
  @PutMapping("/bids/{id}")
  public ResponseEntity<BidList>updateBidList(@PathVariable(value = "id") Integer id,
                                              @Valid @RequestBody BidList bid){
    Optional<BidList>bidIsExist=service.findById(id);

    if (bidIsExist.isEmpty()){
      logger.info("Read - Bid list with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    BidList bidUpdate=service.update(id,bid);
    logger.info("Update - bid list : Account ="+bid.getAccount()
        +", Type ="+bid.getType()+", Bid quantity ="
        +bid.getBidQuantity()+" is saved");

    return new ResponseEntity<>(bidUpdate,HttpStatus.CREATED);
  }

  /**
   Delete bid.

   @param id the id of th bid to delete

   @return the response entity status 200 when the bid is deleted, or status 404 if the bid to delete is not exist
   */
  @DeleteMapping("/bids/{id}")
  public ResponseEntity<BidList>deleteBidList(@PathVariable(value = "id") Integer id){
    Optional<BidList>bidIsExist=service.findById(id);
    if (bidIsExist.isEmpty()){
      logger.info("Read - Bid list with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    service.delete(id);
    logger.info("Delete - bid list with id "+id+" is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

