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
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;

import java.util.List;


import static io.restassured.RestAssured.given;

@Log4j2
public class ApiStepDefs {

    TestContextAPI testContext;
    Scenario scenario;

    @Before
    public void setUp(Scenario s){
        testContext.setScenario(s);
    }

    @Inject
    public ApiStepDefs(TestContextAPI testContext){
        this.testContext = testContext;
        scenario = testContext.getScenario();
        log.debug("Test Context injected in API Step Def constructor and instance variables assigned.");
    }

    @Given("with header as {string}")
    public void with_header_as(String headersAsStringKeyVal) {
        Preconditions.checkNotNull(headersAsStringKeyVal,"Auth Type is Null. Please Fix.");
        Preconditions.checkArgument(!headersAsStringKeyVal.isEmpty(),"No Header Key is defined");
        APIHeaders apiHeaders = new APIHeaders();
        if (headersAsStringKeyVal.trim().equalsIgnoreCase("default")){
            testContext.reqSpec = testContext.reqSpec.headers(apiHeaders.getDefaultHeaders());
        }else{
            testContext.reqSpec = testContext.reqSpec.headers(apiHeaders.appendHeaders(headersAsStringKeyVal));
        }

        scenario.log("Request Headers: " + apiHeaders.toString());
        log.debug("Headers sent from feature file: " + headersAsStringKeyVal + " Header sent in request: " + apiHeaders.toString());
    }

    //!IMPORTANT ==> Needs modification in this method based on what kind of authentication we might have and how to get the token
    @Given("with authorization mechanism as {string}")
    public void with_authorization_mechanism_as(String authType) {
        Preconditions.checkNotNull(authType,"Auth Type is Null. Please Fix.");
        Preconditions.checkArgument(!authType.isEmpty(),"No Auth Type is defined. If your API do not need an auth please type 'na'");
        APIAuth apiAuth = new APIAuth();
        if (authType.trim().equalsIgnoreCase("oauth2")){
            testContext.reqSpec = given().baseUri(testContext.baseUrl).auth().oauth2(apiAuth.getAuthToken());
        }else if(authType.trim().equalsIgnoreCase("basic")){
            testContext.reqSpec = given().baseUri(testContext.baseUrl).auth().basic("userName","password");
        }else if(authType.trim().equalsIgnoreCase("na")){
            testContext.reqSpec = given().baseUri(testContext.baseUrl);
        }
        scenario.log("Auth" + apiAuth.toString());
        log.debug("Auth sent from feature file: " + authType + " Auth sent in request: " + apiAuth.toString());

    }

    @Given("with request end point as {string}")
    public void with_request_end_point_as(String endPoint) {
        Preconditions.checkNotNull(endPoint,"End Point is Null. Please Fix.");
        Preconditions.checkArgument(!endPoint.isEmpty(),"No end Point is defined. From Feature file send end point");
        testContext.endPointUrl = endPoint;
        scenario.log("API end Point: " + endPoint);
        log.debug("API end Point used: " + endPoint);
    }

    @Given("with request body json as")
    public void with_request_body_json_as(String docString) {
        testContext.reqSpec.body(docString);
        scenario.log("Request body sent as: " + docString.toString());
        log.debug("Request body sent as: " + docString.toString());
    }

    @Given("with request body with default request builder for {string}")
    public void with_request_body_with_default_builder(String builderName) {
        //To Construct below Json using Builder pattern
//        {
//            "id": 1,
//            "category": {
//                      "id": 1,
//                    "name": "bull_dog"
//             },
//            "name": "bull dog",
//            "photoUrls": ["url_123", "url_2"],
//            "tags": [
//                {
//                      "id": 1,
//                    "name": "danny"
//                }
//               ],
//            "status": "available"
//        }

//
        //Construction of Request Body using Builder Pattern (Impl Done using Lombok and Jackson under datamodels.pet)
        Category category = Category.builder().withId(1).withName("bull_dog").build();
        Tag tags = Tag.builder().withId(1).withName("dog tag").build();
        List<Tag> listTag = Lists.newArrayList(tags);
        List<String> photoUrlList = Lists.newArrayList("url_1","url_2");

        PetSchema petSchema = PetSchema.builder()
                .withCategory(category)
                .withName("bull dog")
                .withPhotoUrls(photoUrlList)
                .withTags(listTag)
                .build();

        scenario.log(petSchema.toString());
        testContext.reqSpec.body(petSchema);
        scenario.log("Request body sent as: " + petSchema.toString());
        log.debug("Request body sent as: " + petSchema.toString());
    }


    @When("with method as {string}")
    public void with_method_as(String method) {
        Preconditions.checkArgument(
                method.equalsIgnoreCase("get")
                        || method.equalsIgnoreCase("post")
                        || method.equalsIgnoreCase("put")
                        || method.equalsIgnoreCase("delete")
                        || method.equalsIgnoreCase("options")
                        || method.equalsIgnoreCase("patch")
                        || method.equalsIgnoreCase("head")
                        || method.equalsIgnoreCase("trace")
                        || method.equalsIgnoreCase("connect"),
                        "Incorrect HTTP verb sent :" + method);

        testContext.response = testContext.reqSpec.when().post(testContext.endPointUrl);
        scenario.log("Response: " + testContext.response.asString());
        log.debug("Response received as: " + testContext.response.asString());

    }

    @Then("status as {int}")
    public void status_as(Integer statusCode) {
        testContext.response. then().statusCode(statusCode);
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
