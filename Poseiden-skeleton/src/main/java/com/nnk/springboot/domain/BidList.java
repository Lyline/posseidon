package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "TINYINT(4)")
  private Integer id;

  @NotBlank(message = "Account is mandatory")
  @Column(columnDefinition = "VARCHAR(30)")
  private String account;

  @NotBlank(message="Type is mandatory")
  @Column(columnDefinition = "VARCHAR(30)")
  private String type;

  @NotNull(message = "Bid quantity must not be null")
  @DecimalMin(value = "0.1", message="Enter only numbers")
  @Column(columnDefinition = "DOUBLE")
  private double bidQuantity;

  @Column(columnDefinition = "DOUBLE")
  private double askQuantity;

  @Column(columnDefinition = "DOUBLE")
  private double bid;

  @Column(columnDefinition = "DOUBLE")
  private double ask;

  @Column(columnDefinition = "VARCHAR(125)")
  private String benchmark;

  @Column(columnDefinition = "TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime bidListDate;

  @Column(columnDefinition = "VARCHAR(125)")
  private String commentary;

  @Column(columnDefinition = "VARCHAR(125)")
  private String security;

  @Column(columnDefinition = "VARCHAR(10)")
  private String status;

  @Column(columnDefinition = "VARCHAR(125)")
  private String trader;

  @Column(columnDefinition = "VARCHAR(125)")
  private String book;

  @Column(columnDefinition = "VARCHAR(125)")
  private String creationName;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime creationDate;

  @Column(columnDefinition = "VARCHAR(125)")
  private String revisionName;

  @Column(columnDefinition = "VARCHAR(125)")
  @UpdateTimestamp
  private LocalDateTime revisionDate;

  @Column(columnDefinition = "VARCHAR(125)")
  private String dealName;

  @Column(columnDefinition = "VARCHAR(125)")
  private String dealType;

  @Column(columnDefinition = "VARCHAR(125)")
  private String sourceListId;

  @Column(columnDefinition = "VARCHAR(125)")
  private String side;

  public BidList(String account, String type, double bidQuantity) {
    this.account = account;
    this.type = type;
    this.bidQuantity = bidQuantity;
  }
}
