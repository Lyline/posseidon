package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RatingService {
  List<Rating> getAll();
  Rating create(Rating ratingToSave);
  Rating getById(Integer id);
  Optional<Rating> findById(Integer id);
  Rating update(Integer id, Rating ratingToUpdate);
  void delete(Integer id);
}
