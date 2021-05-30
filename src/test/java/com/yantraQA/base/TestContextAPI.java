package com.yantraQA.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.SoftAssertions;

import static io.restassured.RestAssured.given;

@ScenarioScoped
public class TestContextAPI {

    //Common Variables
    @Setter
    @Getter
    Scenario scenario;
    @Inject
    @Getter
    @Setter Config config;
    @Getter
    SoftAssertions softAssertions = new SoftAssertions();

    //API specific Control
    public RequestSpecification reqSpec;
    public Response response;
    public String baseUrl = "https://petstore.swagger.io/v2/";
    public String httpMethod;
    public String endPointUrl;

    @Setter @Getter public Object reqBodyObj;
    @Setter @Getter public Object respBodyObj;

    public TestContextAPI(){
        reqSpec = given();
    }

    public <T> T jsonToObject(String json, T obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        T schema = (T) objectMapper.readValue(json, obj.getClass());
        return schema;
    }



}
