package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "TINYINT(4)")
  private Integer id;

  @Column(columnDefinition = "VARCHAR(125)")
  private String name;

  @Column(columnDefinition = "VARCHAR(125)")
  private String description;

  @Column(columnDefinition = "VARCHAR(125)")
  private String json;

  @Column(columnDefinition = "VARCHAR(512)")
  private String template;

  @Column(columnDefinition = "VARCHAR(125)")
  private String sqlStr;

  @Column(columnDefinition = "VARCHAR(125)")
  private String sqlPart;

  public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
    this.name = name;
    this.description = description;
    this.json = json;
    this.template = template;
    this.sqlStr = sqlStr;
    this.sqlPart = sqlPart;
  }
}
