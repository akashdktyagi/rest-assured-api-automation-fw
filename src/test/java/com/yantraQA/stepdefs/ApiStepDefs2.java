package com.yantraQA.stepdefs;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.yantraQA.base.APIAuth;
import com.yantraQA.base.APIHeaders;
import com.yantraQA.base.TestContextAPI;
import com.yantraQA.datamodels.pet.request.Category;
import com.yantraQA.datamodels.pet.request.PetSchema;
import com.yantraQA.datamodels.pet.request.Tag;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class ApiStepDefs2 {

    TestContextAPI testContext;
    Scenario scenario;

    @Inject
    public ApiStepDefs2(TestContextAPI testContext){
        this.testContext = testContext;
        scenario = testContext.getScenario();
        log.debug("Test Context injected in API Step Def constructor and instance variables assigned.");
    }


    @Then("status as {int}")
    public void status_as(Integer statusCode) {
        testContext.response.then().statusCode(statusCode);
        scenario.log("Status code successfully to be validated: " + statusCode);
        log.debug("Status code as expected: " + statusCode);
    }

    @Then("response schema as {string}")
    public void response_schema_as(String string) {

    }

    @Then("response contains string as {string}")
    public void response_contains_string_as(String string) {
        //testContext.response.then().assertThat().
    }

    @Then("response contains key value pair as {string}")
    public void response_contains_key_value_pair_as(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("response contains data structure as {string}")
    public void response_contains_data_structure_as(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
