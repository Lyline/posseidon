package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
