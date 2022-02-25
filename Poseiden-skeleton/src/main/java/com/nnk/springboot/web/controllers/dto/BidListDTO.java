package com.nnk.springboot.web.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 The type Bid DTO use in the web application.
 */
@Setter
@Getter
@RequiredArgsConstructor
public class BidListDTO {
  private Integer id;
  private String account;
  private String type;
  private double bidQuantity;
}
