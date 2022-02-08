package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
  List<Rating> getAll();
  Rating create(Rating ratingToSave);
  Rating getById(Integer id);
  Rating update(Integer id, Rating ratingToUpdate);
  void delete(Integer id);
}
