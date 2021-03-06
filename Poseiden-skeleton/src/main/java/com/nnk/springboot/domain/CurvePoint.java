package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 The type Curve point.
 */
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
  @NotNull(message="Curve Id must not be null")
  @Min(value = 1, message = "Curve Id must be minimum equal to 1")
  private Integer curveId;

  @Column(columnDefinition = "TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime asOfDate;

  @Column(columnDefinition = "DOUBLE")
  @DecimalMin(value = "0.1",message = "Term must be minimum equal to 0.1")
  private double term;

  @Column(columnDefinition = "DOUBLE")
  @NotNull
  @Min(value = 1,message = "Value must not be null")
  private double value;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime creationDate;

  /**
   Instantiates a new Curve point with the mandatory fields.

   @param curveId the curve id
   @param term    the term
   @param value   the value
   */
  public CurvePoint(Integer curveId, double term, double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
  }
}
