/*
 * Copyright (C) 2020 adidas AG.
 */

package com.adidas.presentationrestassured.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * @author Oleg Pinta
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerIT {
  @LocalServerPort
  protected int port;

  @BeforeEach
  public void setup() {
    RestAssured.port = port;
  }
}
