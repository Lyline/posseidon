package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{

  private final RatingRepository repository;

  public RatingServiceImpl(RatingRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public List<Rating> getAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Rating create(Rating ratingToSave) {
    return repository.save(ratingToSave);
  }

  @Override
  @Transactional
  public Rating getById(Integer id) {
    return repository.getById(id);
  }

  @Override
  @Transactional
  public Rating update(Integer id, Rating ratingToUpdate) {
    ratingToUpdate.setId(id);
    return repository.save(ratingToUpdate);
  }

  @Override
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
