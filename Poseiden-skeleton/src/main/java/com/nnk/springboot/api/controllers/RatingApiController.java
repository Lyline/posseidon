package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 The type Rating api controller.
 */
@RestController
@RequestMapping("/api")
public class RatingApiController extends HandlerException {

  private final RatingServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(RatingApiController.class);

  /**
   Instantiates a new Rating api controller.

   @param service the service
   */
  public RatingApiController(RatingServiceImpl service) {
    this.service = service;
  }

  /**
   Get all ratings.

   @return the response entity status 200 with a list of ratings, or status 204 with an empty list
   */
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

  /**
   Add rating.

   @param rating the rating

   @return the response entity status 201 with the rating object saved
   */
  @PostMapping("/ratings")
  public ResponseEntity<Rating>addRating(@Valid @RequestBody Rating rating){
    Rating ratingSave=service.create(rating);
    logger.info("Create - rating : Moody's rating ="+ rating.getMoodysRating()+
        ", Sand P Rating ="+ rating.getSandPRating()+", Fitch rating ="+ rating.getFitchRating()+
        ", Order number ="+ rating.getOrderNumber()+" is saved");
    return new ResponseEntity<>(ratingSave,HttpStatus.CREATED);
  }

  /**
   Update rating.

   @param id     the id of rating to update
   @param rating the rating submitted

   @return the response entity status 201 with the rating object updated, or status 404 if the rating is not exist
   */
  @PutMapping("/ratings/{id}")
  public ResponseEntity<Rating>updateRating(@PathVariable(value = "id") Integer id,
                                            @Valid @RequestBody Rating rating){
    Optional<Rating> ratingIsExist=service.findById(id);
    if (ratingIsExist.isEmpty()){
      logger.info("Read - rating with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Rating curveUpdate=service.update(id,rating);
    logger.info("Update - rating : Moody's rating ="+ rating.getMoodysRating()+
        ", Sand P Rating ="+ rating.getSandPRating()+", Fitch rating ="+ rating.getFitchRating()+
        ", Order number ="+ rating.getOrderNumber()+" is saved");
    return new ResponseEntity<>(curveUpdate,HttpStatus.CREATED);
  }

  /**
   Delete rating.

   @param id the id of rating to delete

   @return the response entity status 200 when the rating is deleted, or status 404 if the rating is not exist
   */
  @DeleteMapping("/ratings/{id}")
  public ResponseEntity<Rating> deleteRating(@PathVariable(value = "id") Integer id){
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
