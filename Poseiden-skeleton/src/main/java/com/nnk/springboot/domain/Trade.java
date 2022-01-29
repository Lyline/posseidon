package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "TINYINT(4)")
  private Integer tradeId;

  @NotNull
  @Column(columnDefinition = "VARCHAR(30)")
  private String account;

  @NotNull
  @Column(columnDefinition = "VARCHAR(30)")
  private String type;

  @Column(columnDefinition = "DOUBLE")
  private double buyQuantity;

  @Column(columnDefinition = "DOUBLE")
  private double sellQuantity;

  @Column(columnDefinition = "DOUBLE")
  private double buyPrice;

  @Column(columnDefinition = "DOUBLE")
  private double sellPrice;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime tradeDate;

  @Column(columnDefinition = "VARCHAR(125)")
  private String security;

  @Column(columnDefinition = "VARCHAR(10)")
  private String status;

  @Column(columnDefinition = "VARCHAR(125)")
  private String trader;

  @Column(columnDefinition = "VARCHAR(125)")
  private String benchmark;

  @Column(columnDefinition = "VARCHAR(125)")
  private String book;

  @Column(columnDefinition = "VARCHAR(125)")
  private String creationName;

  @Column(columnDefinition = "TIMESTAMP")
  private String creationDate;

  @Column(columnDefinition = "VARCHAR(125)")
  private String revisionName;

  @Column(columnDefinition = "TIMESTAMP")
  private String revisionDate;

  @Column(columnDefinition = "VARCHAR(125)")
  private String dealName;

  @Column(columnDefinition = "VARCHAR(125)")
  private String dealType;

  @Column(columnDefinition = "VARCHAR(125)")
  private String sourceListId;

  @Column(columnDefinition = "VARCHAR(125)")
  private String side;

  public Trade(String account, String type) {
    this.account = account;
    this.type = type;
  }
}
