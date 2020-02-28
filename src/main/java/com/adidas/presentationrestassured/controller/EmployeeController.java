package com.adidas.presentationrestassured.controller;

import com.adidas.presentationrestassured.dto.EmployeeDto;
import com.adidas.presentationrestassured.service.EmployeeService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public PagedModel<EntityModel<EmployeeDto>> getAllEmployees(PagedResourcesAssembler<EmployeeDto> pagedResourcesAssembler) {
    List<EmployeeDto> dtos = employeeService.findAll();
    return pagedResourcesAssembler.toModel(new PageImpl<>(dtos));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
    EmployeeDto dto = employeeService.findById(id);
    if (dto == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(dto, HttpStatus.OK);
    }
  }

  @PostMapping
  public EmployeeDto createEmployee(@RequestBody EmployeeDto dto) {
    return employeeService.create(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    if (employeeService.delete(id)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
