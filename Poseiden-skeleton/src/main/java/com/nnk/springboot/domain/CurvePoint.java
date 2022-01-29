package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "TINYINT(4)")
  private Integer id;

  @Column(columnDefinition = "TINYINT")
  private Integer curveId;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime asOfDate;

  @Column(columnDefinition = "DOUBLE")
  private double term;

  @Column(columnDefinition = "DOUBLE")
  private double value;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime creationDate;

  public CurvePoint(Integer curveId, double term, double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
  }
}
