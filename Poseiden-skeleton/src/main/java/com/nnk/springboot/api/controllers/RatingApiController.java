package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

  @PutMapping("/ratings/{id}")
  public ResponseEntity<Rating>updateRating(@PathVariable(value = "id") Integer id,
                                                 @RequestBody Rating rating){
    Optional<Rating> ratingIsExist=service.findById(id);
    if (ratingIsExist.isEmpty()){
      logger.info("Read - rating with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (rating.getMoodysRating().isBlank() | rating.getSandPRating().isBlank() |
        rating.getFitchRating().isBlank() | rating.getOrderNumber()==null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Rating curveUpdate=service.update(id,rating);
    logger.info("Update - rating : Moody's rating ="+ rating.getMoodysRating()+"," +
        " Sand P Rating ="+ rating.getSandPRating()+", Fitch rating ="+ rating.getFitchRating()+
        "Order number ="+ rating.getOrderNumber()+" is saved");
    return new ResponseEntity<>(curveUpdate,HttpStatus.CREATED);
  }

  @DeleteMapping("/ratings/{id}")
  public ResponseEntity<Rating>deleteBidList(@PathVariable(value = "id") Integer id){
    Optional<Rating>ratingIsExist=service.findById(id);
    if (ratingIsExist.isEmpty()){
      logger.info("Read - rating with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    service.delete(id);
    logger.info("Delete - rating with id "+id+" is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
