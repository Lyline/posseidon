package com.nnk.springboot.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CurvePointDTO {
  private Integer id;
  private Integer curveId;
  private double term;
  private double value;
}
