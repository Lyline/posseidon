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
}
