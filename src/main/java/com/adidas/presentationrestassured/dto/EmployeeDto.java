package com.adidas.presentationrestassured.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EmployeeDto extends RepresentationModel<EmployeeDto> {
  private Long id;
  private String name;
  private LocalDate dateOfBirth;
  private Set<RoleDto> roles;
}
