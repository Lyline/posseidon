package com.nnk.springboot.web.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class BidListDTO {
  private Integer id;
  private String account;
  private String type;
  private double bidQuantity;
}
