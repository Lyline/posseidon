package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CurvePointService {
  List<CurvePoint>getAll();
  CurvePoint create(CurvePoint curvePoint);
  CurvePoint getById(Integer id);
  Optional<CurvePoint> findById(Integer id);
  CurvePoint update(Integer id, CurvePoint curveToUpdate);
  void delete(Integer id);
}
