package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 The interface Curve point service.
 */
@Service
public interface CurvePointService {
  /**
   Get all curve point.

   @return the list of curve points
   */
  List<CurvePoint>getAll();

  /**
   Create curve point.

   @param curvePoint the curve point

   @return the curve point object created
   */
  CurvePoint create(CurvePoint curvePoint);

  /**
   Gets curve point by id.

   @param id the id of the curve point

   @return the curve point if it exists or null
   */
  CurvePoint getById(Integer id);

  /**
   Find curve point by id.

   @param id the id

   @return the optional, return curve point objet if it exists or optional is empty
   */
  Optional<CurvePoint> findById(Integer id);

  /**
   Update curve point by id.

   @param id            the id of the curve point
   @param curveToUpdate the curve to update

   @return the curve point updated
   */
  CurvePoint update(Integer id, CurvePoint curveToUpdate);

  /**
   Delete curve point by id.

   @param id the id of the curve point
   */
  void delete(Integer id);
}
