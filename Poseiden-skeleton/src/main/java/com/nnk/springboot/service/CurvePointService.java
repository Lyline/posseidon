package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurvePointService {
  List<CurvePoint>getAll();
  CurvePoint create(CurvePoint curvePoint);
}
