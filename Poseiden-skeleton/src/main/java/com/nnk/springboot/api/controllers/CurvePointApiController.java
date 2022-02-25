package com.nnk.springboot.api.controllers;

import com.nnk.springboot.api.controllers.exception.HandlerException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 The type Curve point api controller.
 */
@RestController
@RequestMapping("/api")
public class CurvePointApiController extends HandlerException {

  private final CurvePointServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(CurvePointApiController.class);

  /**
   Instantiates a new Curve point api controller.

   @param service the service
   */
  public CurvePointApiController(CurvePointServiceImpl service) {
    this.service = service;
  }

  /**
   Get all curve points.

   @return the response entity status 200 with the list of curve point, or status 204 with an empty list
   */
  @GetMapping("/curvePoints")
  public ResponseEntity<List<CurvePoint>> getAllCurvePoints(){
    List<CurvePoint>curves=service.getAll();
    if (curves.isEmpty()){
      logger.info("Read - No curve point saved ");
      return new ResponseEntity<>(curves, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All curve point : "+curves.size()+" curve point(s)");
      return new ResponseEntity<>(curves, HttpStatus.OK);
    }
  }

  /**
   Add curve point.

   @param curve the curve

   @return the response entity status 201 and curve point object saved
   */
  @PostMapping("/curvePoints")
  public ResponseEntity<CurvePoint>addCurvePoint(@Valid @RequestBody CurvePoint curve){
    CurvePoint curveSave=service.create(curve);

    logger.info("Create - curve point : Curve id ="+curve.getCurveId()+", Term ="
        + curve.getTerm()+", Value ="+ curve.getValue()+" is saved");
    return new ResponseEntity<>(curveSave,HttpStatus.CREATED);
  }

  /**
   Update curve point.

   @param id    the id of curve point to update
   @param curve the curve submitted

   @return the response entity status 201 with curve point object updated, or status 404 if the curve point is not exist
   */
  @PutMapping("/curvePoints/{id}")
  public ResponseEntity<CurvePoint>updateCurvePoint(@PathVariable(value = "id") Integer id,
                                                    @Valid @RequestBody CurvePoint curve){
    Optional<CurvePoint> curveIsExist=service.findById(id);
    if (curveIsExist.isEmpty()){
      logger.info("Read - curve point with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    CurvePoint curveUpdate=service.update(id,curve);
    logger.info("Update - curve point : Curve id ="+curve.getCurveId()+", Term ="
        + curve.getTerm()+", Value ="+ curve.getValue()+" is saved");
    return new ResponseEntity<>(curveUpdate,HttpStatus.CREATED);
  }

  /**
   Delete curve point.

   @param id the id of curve point to delete

   @return the response entity status 200 when the curve point is deleted, or status 404 if the curve point is not exist
   */
  @DeleteMapping("/curvePoints/{id}")
  public ResponseEntity<BidList>deleteCurvePoint(@PathVariable(value = "id") Integer id){
    Optional<CurvePoint>curveIsExist=service.findById(id);
    if (curveIsExist.isEmpty()){
      logger.info("Read - curve list with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    service.delete(id);
    logger.info("Delete - curve list with id "+id+" is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
