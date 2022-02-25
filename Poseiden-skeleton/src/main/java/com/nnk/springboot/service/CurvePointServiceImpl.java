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
public class CurvePointServiceImpl implements CurvePointService {
  private final CurvePointRepository repository;

  /**
   Instantiates a new Curve point service.

   @param repository the repository
   */
  public CurvePointServiceImpl(CurvePointRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public List<CurvePoint> getAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public CurvePoint create(CurvePoint curvePoint) {
    return repository.save(curvePoint);
  }

  @Override
  @Transactional
  public CurvePoint getById(Integer id) {
    return repository.getById(id);
  }

  @Override
  @Transactional
  public Optional<CurvePoint> findById(Integer id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public CurvePoint update(Integer id, CurvePoint curveToUpdate) {
    curveToUpdate.setId(id);
    return repository.save(curveToUpdate);
  }

  @Override
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
