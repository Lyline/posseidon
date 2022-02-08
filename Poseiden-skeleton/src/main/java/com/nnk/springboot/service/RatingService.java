package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
  List<Rating> getAll();
}
