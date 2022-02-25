package com.nnk.springboot.web.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 The type Trade DTO use in the web application.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class TradeDTO {
  private Integer tradeId;
  private String account;
  private String type;
  private double buyQuantity;
}
