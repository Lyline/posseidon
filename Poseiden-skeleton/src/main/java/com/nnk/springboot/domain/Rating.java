package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
  private String moodysRating;

  @Column(columnDefinition = "VARCHAR(125)")
  private String sandPRating;

  @Column(columnDefinition = "VARCHAR(125)")
  private String fitchRating;

  @Column(columnDefinition = "TINYINT")
  private Integer orderNumber;

  public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
    this.moodysRating = moodysRating;
    this.sandPRating = sandPRating;
    this.fitchRating = fitchRating;
    this.orderNumber = orderNumber;
  }
}
