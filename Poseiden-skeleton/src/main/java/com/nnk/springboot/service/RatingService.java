package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 The interface Rating service.
 */
@Service
public interface RatingService {
  /**
   Gets all ratings.

   @return the list of the ratings
   */
  List<Rating> getAll();

  /**
   Create rating.

   @param ratingToSave the rating to save

   @return the rating object created
   */
  Rating create(Rating ratingToSave);

  /**
   Gets rating by id.

   @param id the id of the rating

   @return the rating object if it exists or null
   */
  Rating getById(Integer id);

  /**
   Find rating by id.

   @param id the id of the rating

   @return the optional, return rating if it exists or optional is empty
   */
  Optional<Rating> findById(Integer id);

  /**
   Update rating by id.

   @param id             the id of the rating
   @param ratingToUpdate the rating to update

   @return the rating object updated
   */
  Rating update(Integer id, Rating ratingToUpdate);

  /**
   Delete rating by id.

   @param id the id of the rating
   */
  void delete(Integer id);
}
