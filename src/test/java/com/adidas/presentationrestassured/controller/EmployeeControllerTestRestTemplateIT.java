package com.adidas.presentationrestassured.controller;

import static com.adidas.presentationrestassured.helper.TestHelper.getJsonObjectFromFile;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import com.adidas.presentationrestassured.dao.EmployeeRepository;
import com.adidas.presentationrestassured.dto.EmployeeDto;
import com.adidas.presentationrestassured.entity.Employee;
import com.adidas.presentationrestassured.entity.Role;

class EmployeeControllerTestRestTemplateIT extends BaseControllerIT {

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  EmployeeRepository employeeRepository;

  @Test
  void getAllEmployees() {

    //prepare
    createEmployeeInDatabase();
    final var totalEmployees = employeeRepository.findAll().size();

    // get
    final var responseEntity = restTemplate
        .exchange(format("http://localhost:%s/employees", this.port), HttpMethod.GET,
            null, PagedModel.class);

    // check
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
    assertThat(responseEntity.getBody().getMetadata().getTotalElements()).isEqualTo(totalEmployees);

  }

  @Test
  void getEmployeeById() {
    // prepare
    final var employeeId = createEmployeeInDatabase();

    // get
    final var responseEntity = restTemplate
        .exchange(format("http://localhost:%s/employees/%s", this.port, employeeId), HttpMethod.GET,
            null, EmployeeDto.class);

    //check
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
    assertThat(responseEntity.getBody()).extracting(EmployeeDto::getId).isEqualTo(employeeId);

  }

  @Test
  void createEmployee() throws IOException, ParseException {
    // prepare data
    JSONObject jsonObject = getJsonObjectFromFile("json/employee.json");

    // create
    final var response = restTemplate
        .exchange(format("http://localhost:%s/employees", this.port), HttpMethod.POST,
            new HttpEntity<>(jsonObject), EmployeeDto.class);

    //check
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
    assertThat(response.getBody()).extracting(EmployeeDto::getId).isNotNull();
    assertThat(response.getBody()).extracting(EmployeeDto::getName).isEqualTo("Test name");
  }

  @Test
  void deleteEmployee() throws IOException, ParseException {
    // prepare
    final var employeeId = createEmployeeInDatabase();

    // delete
    final var response = restTemplate
        .exchange(format("http://localhost:%s/employees/%s", this.port, employeeId), HttpMethod.DELETE, null, Void.class);

     //check
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NO_CONTENT);

     // check
    final var getByIdResponse = restTemplate
        .exchange(String.format("http://localhost:%s/employees/%s", this.port, employeeId), HttpMethod.GET,
            null, EmployeeDto.class);

    assertThat(getByIdResponse).isNotNull();
    assertThat(getByIdResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NOT_FOUND);

  }

  @Test
  void deleteNonexistentEmployeeShouldReturn404() {
    // delete
    final var response = restTemplate
        .exchange(format("http://localhost:%s/employees/%s", this.port, 100000), HttpMethod.DELETE, null, Void.class);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NOT_FOUND);
  }


  public Long createEmployeeInDatabase() {
    final var employee = new Employee();
    employee.setDateOfBirth(LocalDate.of(1800, 12, 5));
    final var role = new Role();
    role.setId(1L);
    role.setName("admin");
    employee.setRoles(Set.of(role));

    return employeeRepository.create(employee).getId();
  }
}