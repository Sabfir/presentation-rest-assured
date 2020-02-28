/*
 * Copyright (C) 2020 adidas AG.
 */

package com.adidas.presentationrestassured.helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * @author Oleg Pinta
 */
public class TestHelper {
  public static final String APPLICATION_JSON_VALUE = "application/json";

  public static JSONObject getJsonObjectFromFile(String filePath)
      throws IOException, ParseException {
    JSONParser jsonParser = new JSONParser();
    return (JSONObject) jsonParser.parse(new FileReader(getFileFromResources(filePath)));
  }

  public static File getFileFromResources(String path) {
    return new File(TestHelper.class.getClassLoader().getResource(path).getFile());
  }

  public static int getRandomNumberInRange(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }

    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }
}
