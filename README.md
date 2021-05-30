# Rest Assured API Framework
---
> Author: Akash Tyagi
>
> Date: 27th May 2021
---

* This framework can be used a base framework for any API Automation Project.
* I have implemented below libraries:
     * Cucumber: For Test Scenario Creation
     * Rest Assured: For hitting and validating the api responses
     * Extent Report GrassHopper Cucumber Adpater: For non-intrusive Extent Reporting. Check the ref here: https://grasshopper.tech/2098/
     * Google Guava Library: For Preconditions and other useful classes and methods which Guava lib provides like Pre-conditions checkNull / CheckArguments etc
     * Google Guice Library: For Dependency Injection of Test Context object so that ReqSpec and Response object can be shared across multiple Step Defs.
     * Builder Design Pattern/ObjetMapper Class: For Mapping Request and Response JSON with POJO.
    

* Use Swagger rest assured lib for validating the request response or schema:
  https://bitbucket.org/atlassian/swagger-request-validator/src/master/swagger-request-validator-examples/src/test/java/com/atlassian/oai/validator/examples/restassured/OpenApiValidationFilterTestExample.java?at=master
  * Example on How to use it: (fetched from above link)
  
```java
package com.atlassian.oai.validator.examples.restassured;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;

/**
 * An example that uses the {@link OpenApiValidationFilter} to validate request/response interactions
 * mediated by the REST-Assured library against a Swagger API specification.
 * <p>
 * The filter can be applied to any REST-Assured given-when-then interaction and allows developers to test
 * that their REST service implementation matches their API specification. This is particularly useful when using
 * a design-first approach where the implementation is separate from the specification. However, even in cases where
 * the specification is generated from the implementation this can yield benefits, as a lot of the information in
 * the specification comes from metadata applied to the implementation (e.g. via annotations on the resource methods)
 * which are not checked at compile time.
 */
public class OpenApiValidationFilterTestExample {

    private static final String SWAGGER_JSON_URL = "http://petstore.swagger.io/v2/swagger.json";
    private static final int PORT = 9999;

    private final OpenApiValidationFilter validationFilter = new OpenApiValidationFilter(SWAGGER_JSON_URL);

    // Using wiremock to simulate a production service.
    // In a real-world use case you would call out to your service (e.g. in a Spring WebMVC test,
    // or to a service running in your TEST environment etc.)
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    @Before
    public void setup() {
        wireMockRule.stubFor(
                WireMock.get(urlEqualTo("/pet/1"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("content-type", "application/json")
                                .withBody("{\"name\":\"fido\", \"photoUrls\":[]}")));

        wireMockRule.stubFor(
                WireMock.get(urlEqualTo("/pet/2"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("content-type", "application/json")
                                .withBody("{\"name\":\"fido\"}"))); // Missing required 'photoUrls' field

        wireMockRule.stubFor(
                WireMock.get(urlEqualTo("/pet/fido")) // Invalid petId
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("content-type", "application/json")
                                .withBody("{\"name\":\"fido\", \"photoUrls\":[]}")));
    }

    /**
     * Test a GET with a valid request/response
     * <p>
     * This test is expected to PASS
     */
    @Test
    public void testGetValidPet() {
        given()
                .port(PORT)
                .filter(validationFilter)
                .header("api_key", "foo")
                .when()
                .get("/pet/1")
                .then()
                .assertThat()
                .statusCode(200);
    }

    /**
     * Test a GET with an invalid request/response expectation.
     * <p>
     * This test will pass the business logic tests, but the validation filter will fail the test because the
     * response received from the server doesn't match the schema defined in the API specification.
     * <p>
     * This could be due to a bug in the implementation, or a problem in the API specification.
     * Regardless - something is wrong and should be addressed.
     * <p>
     * This test is expected to FAIL
     */
    @Test
    public void testGetInvalidPet() {
        given()
                .port(PORT)
                .filter(validationFilter)
                .header("api_key", "foo")
                .when()
                .get("/pet/2")
                .then()
                .assertThat()
                .statusCode(200);
    }

    /**
     * Test a GET with an invalid request/response expectation.
     * <p>
     * This test will pass the business logic tests, but the validation filter will fail the test because even though
     * the server returned a valid result the request used a path parameter that does not match the schema defined
     * in the API specification.
     * <p>
     * This could be due to a bug in the implementation, or a problem in the API specification.
     * Regardless - something is wrong and should be addressed.
     * <p>
     * This test is expected to FAIL
     */
    @Test
    public void testGetWithInvalidId() {
        given()
                .port(PORT)
                .filter(validationFilter)
                .header("api_key", "foo")
                .when()
                .get("/pet/fido")
                .then()
                .assertThat()
                .statusCode(200);
    }

}

```
    