package com.adidas.presentationrestassured.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class Employee {
  private Long id;
  private String name;
  private LocalDate dateOfBirth;
  private Set<Role> roles;
}
