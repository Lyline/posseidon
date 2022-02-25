package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 The type Rating.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "TINYINT(4)")
  private Integer id;

  @Column(columnDefinition = "VARCHAR(125)")
  @NotBlank(message = "Moody's rating must not be empty")
  private String moodysRating;

  @Column(columnDefinition = "VARCHAR(125)")
  @NotBlank(message = "Sand rating must not be empty")
  private String sandPRating;

  @Column(columnDefinition = "VARCHAR(125)")
  @NotBlank(message = "Fitch rating must not be empty")
  private String fitchRating;

  @Column(columnDefinition = "TINYINT")
  @NotNull(message = "Order number must not be null")
  private Integer orderNumber;

  /**
   Instantiates a new Rating.

   @param moodysRating the moodys rating
   @param sandPRating  the sand p rating
   @param fitchRating  the fitch rating
   @param orderNumber  the order number
   */
  public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
    this.moodysRating = moodysRating;
    this.sandPRating = sandPRating;
    this.fitchRating = fitchRating;
    this.orderNumber = orderNumber;
  }
}
