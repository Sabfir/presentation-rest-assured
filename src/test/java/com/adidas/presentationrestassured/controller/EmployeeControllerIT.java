package com.adidas.presentationrestassured.controller;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.adidas.presentationrestassured.helper.TestHelper.APPLICATION_JSON_VALUE;
import static com.adidas.presentationrestassured.helper.TestHelper.getJsonObjectFromFile;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

class EmployeeControllerIT extends BaseControllerIT {

  @Test
  void getAllEmployees() throws IOException, ParseException {
    // prepare
    int expectedSize = findAll().body().path("_embedded.employeeDtoList.size()");
    createEmployee();
    expectedSize++;

    // get and check
    when().
        get("employees").
    then().
        statusCode(HttpStatus.SC_OK).
        contentType(HAL_JSON_VALUE).
        body("_embedded.employeeDtoList.size()", is(expectedSize));
  }

  @Test
  void getEmployeeById() throws IOException, ParseException {
    // prepare
    createEmployee();
    List<Integer> ids = findAll().body().jsonPath().get("_embedded.employeeDtoList.id");
    int id = ids.get(0);

    // get and check
    when().
        get("/employees/{id}", id).
    then().
        statusCode(HttpStatus.SC_OK).
        contentType(HAL_JSON_VALUE).
        body("id", is(id));
  }

  @Test
  void createEmployee() throws IOException, ParseException {
    // prepare data
    JSONObject jsonObject = getJsonObjectFromFile("json/employee.json");

    // create and check
    given().
        contentType(APPLICATION_JSON_VALUE).
        body(jsonObject.toString()).
    when().
        post("/employees").
    then().
        statusCode(HttpStatus.SC_OK).
        contentType(HAL_JSON_VALUE);
  }

  @Test
  void deleteEmployee() throws IOException, ParseException {
    // prepare
    createEmployee();
    List<Integer> ids = findAll().body().jsonPath().get("_embedded.employeeDtoList.id");
    int id = ids.get(0);

    // delete
    when().
        delete("/employees/{id}", id).
    then().
        statusCode(HttpStatus.SC_NO_CONTENT);

     // check
    when().
        get("/employees/{id}", id).
    then().
        statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void deleteNonexistentEmployeeShouldReturn404() {
    // delete
    when().
        delete("/employees/{id}", 100000).
    then().
        statusCode(HttpStatus.SC_NOT_FOUND);
  }

  private Response findAll() {
    return when().
        get("/employees").
    then().
        statusCode(HttpStatus.SC_OK).
        contentType(HAL_JSON_VALUE).
    extract().
        response();
  }
}