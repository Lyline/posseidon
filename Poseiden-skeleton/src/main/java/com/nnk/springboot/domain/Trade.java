package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 The type Trade.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "TINYINT(4)")
  private Integer tradeId;

  @NotBlank(message = "Account must not be empty")
  @Column(columnDefinition = "VARCHAR(30)")
  private String account;

  @NotBlank(message = "Type must not be empty")
  @Column(columnDefinition = "VARCHAR(30)")
  private String type;

  @DecimalMin(value="0.1", message = "Buy quantity must not be minimum equal to 0.1")
  @Column(columnDefinition = "DOUBLE")
  private double buyQuantity;

  @Column(columnDefinition = "DOUBLE")
  private double sellQuantity;

  @Column(columnDefinition = "DOUBLE")
  private double buyPrice;

  @Column(columnDefinition = "DOUBLE")
  private double sellPrice;

  @Column(columnDefinition = "TIMESTAMP")
  @CreationTimestamp
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

  /**
   Instantiates a new Trade.

   @param account     the account
   @param type        the type
   @param buyQuantity the buy quantity
   */
  public Trade(String account, String type, double buyQuantity) {
    this.account = account;
    this.type = type;
    this.buyQuantity = buyQuantity;
  }
}
