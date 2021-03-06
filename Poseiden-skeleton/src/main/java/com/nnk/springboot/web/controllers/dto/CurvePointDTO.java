package com.nnk.springboot.web.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 The type Curve point DTO use in the web application.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class CurvePointDTO {
  private Integer id;
  private Integer curveId;
  private double term;
  private double value;
}
