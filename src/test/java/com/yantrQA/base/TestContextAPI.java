package com.yantrQA.base;

import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

@ScenarioScoped
public class TestContextAPI {

    //API specific Control
    public RequestSpecification reqSpec;
    public Response response;
    public String baseUrl = "https://petstore.swagger.io/v2/";
    public String httpMethod;
    public String endPointUrl;

    public TestContextAPI(){
        reqSpec = given();
    }

    //Common Variables
    @Setter @Getter Scenario scenario;
    @Inject @Getter @Setter Config config;


}
