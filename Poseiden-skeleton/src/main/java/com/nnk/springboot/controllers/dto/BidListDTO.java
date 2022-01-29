package com.nnk.springboot.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class BidListDTO {
  private Integer bidListId;
  private String account;
  private String type;
  private double bidQuantity;
}
