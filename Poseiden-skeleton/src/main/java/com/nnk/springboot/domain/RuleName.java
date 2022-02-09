package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

  @NotBlank(message = "Name must not be empty")
  @Column(columnDefinition = "VARCHAR(125)")
  private String name;

  @NotBlank(message = "Description must not be empty")
  @Column(columnDefinition = "VARCHAR(125)")
  private String description;

  @NotBlank(message = "Json must not be empty")
  @Column(columnDefinition = "VARCHAR(125)")
  private String json;

  @NotBlank(message = "Template must not be empty")
  @Column(columnDefinition = "VARCHAR(512)")
  private String template;

  @NotBlank(message = "SQL String must not be empty")
  @Column(columnDefinition = "VARCHAR(125)")
  private String sqlStr;

  @NotBlank(message = "SQL Part must not be empty")
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
