package com.videogames.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import com.videogames.config.ConfigurationReader;

public class BaseTest {
    
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigurationReader.getBaseUrl();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}