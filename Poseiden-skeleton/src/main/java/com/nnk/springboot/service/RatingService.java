package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 The type Rating service implementation.
 */
@Service
public class RatingService {

  private final RatingRepository repository;

  /**
   Instantiates a new Rating service.

   @param repository the repository
   */
  public RatingService(RatingRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public List<Rating> getAll() {
    return repository.findAll();
  }

  @Transactional
  public Rating create(Rating ratingToSave) {
    return repository.save(ratingToSave);
  }

  @Transactional
  public Rating getById(Integer id) {
    return repository.getById(id);
  }

  @Transactional
  public Optional<Rating> findById(Integer id) {
    return repository.findById(id);
  }

  @Transactional
  public Rating update(Integer id, Rating ratingToUpdate) {
    ratingToUpdate.setId(id);
    return repository.save(ratingToUpdate);
  }

  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
