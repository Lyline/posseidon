package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RatingApiController {

  private final RatingServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(RatingApiController.class);

  public RatingApiController(RatingServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/ratings")
  public ResponseEntity<List<Rating>> getAllRatings(){
    List<Rating>ratings=service.getAll();
    if (ratings.isEmpty()){
      logger.info("Read - No rating saved ");
      return new ResponseEntity<>(ratings, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All rating : "+ratings.size()+" rating(s)");
      return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
  }

  @PostMapping("/ratings")
  public ResponseEntity<Rating>addRating(@RequestBody Rating rating){
    if (rating.getMoodysRating().isBlank() | rating.getSandPRating().isBlank() |
        rating.getFitchRating().isBlank() | rating.getOrderNumber()==null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Rating ratingSave=service.create(rating);
    logger.info("Create - rating : Moody's rating ="+ rating.getMoodysRating()+"," +
        " Sand P Rating ="+ rating.getSandPRating()+", Fitch rating ="+ rating.getFitchRating()+
        "Order number ="+ rating.getOrderNumber()+" is saved");
    return new ResponseEntity<>(ratingSave,HttpStatus.CREATED);
  }

}
