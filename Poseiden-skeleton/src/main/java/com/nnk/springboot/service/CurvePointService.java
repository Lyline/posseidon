package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 The type Curve point service implementation.
 */
@Service
public class CurvePointService {
  private final CurvePointRepository repository;

  /**
   Instantiates a new Curve point service.

   @param repository the repository
   */
  public CurvePointService(CurvePointRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public List<CurvePoint> getAll() {
    return repository.findAll();
  }

  @Transactional
  public CurvePoint create(CurvePoint curvePoint) {
    return repository.save(curvePoint);
  }

  @Transactional
  public CurvePoint getById(Integer id) {
    return repository.getById(id);
  }

  @Transactional
  public Optional<CurvePoint> findById(Integer id) {
    return repository.findById(id);
  }

  @Transactional
  public CurvePoint update(Integer id, CurvePoint curveToUpdate) {
    curveToUpdate.setId(id);
    return repository.save(curveToUpdate);
  }

  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
