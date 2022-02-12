package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CurvePointApiController {

  private final CurvePointServiceImpl service;
  private final Logger logger= LoggerFactory.getLogger(CurvePointApiController.class);

  public CurvePointApiController(CurvePointServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/curvePoints")
  public ResponseEntity<List<CurvePoint>>getAllCurvePoint(){
    List<CurvePoint>curves=service.getAll();
    if (curves.isEmpty()){
      logger.info("Read - No curve point saved ");
      return new ResponseEntity<>(curves, HttpStatus.NO_CONTENT);
    }else{
      logger.info("Read - All curve point : "+curves.size()+" curve point(s)");
      return new ResponseEntity<>(curves, HttpStatus.OK);
    }
  }

  @PostMapping("/curvePoints")
  public ResponseEntity<CurvePoint>addCurvePoint(@RequestBody CurvePoint curve){
    if (curve.getCurveId()==null | curve.getTerm()==0 | curve.getValue()==0){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    CurvePoint curveSave=service.create(curve);
    logger.info("Create - curve point : Curve id ="+curve.getCurveId()+", Term ="
        + curve.getTerm()+", Value ="+ curve.getValue()+" is saved");
    return new ResponseEntity<>(curveSave,HttpStatus.CREATED);
  }

  @PutMapping("/curvePoints/{id}")
  public ResponseEntity<CurvePoint>updateBidList(@PathVariable(value = "id") Integer id,
                                              @RequestBody CurvePoint curve){
    Optional<CurvePoint> curveIsExist=service.findById(id);
    if (curveIsExist.isEmpty()){
      logger.info("Read - curve point with id "+id+" is not exist");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (curve.getCurveId()==null | curve.getTerm()==0 | curve.getValue()==0){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    CurvePoint curveUpdate=service.update(id,curve);
    logger.info("Update - curve point : Curve id ="+curve.getCurveId()+", Term ="
        + curve.getTerm()+", Value ="+ curve.getValue()+" is saved");
    return new ResponseEntity<>(curveUpdate,HttpStatus.CREATED);
  }

  @DeleteMapping("/curvePoints/{id}")
  public ResponseEntity<BidList>deleteBidList(@PathVariable(value = "id") Integer id){
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
