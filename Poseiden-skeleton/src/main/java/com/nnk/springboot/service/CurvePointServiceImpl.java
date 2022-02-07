package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {
  private final CurvePointRepository repository;

  public CurvePointServiceImpl(CurvePointRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<CurvePoint> getAll() {
    return repository.findAll();
  }
}
