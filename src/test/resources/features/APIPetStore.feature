@api
Feature: API Pet Store Feature

#    Given with authorization mechanism as "outh2"
#    And with header as "key1=val1,key2=val2"
  Background: API Authorization
    Given with authorization mechanism as "na"
    And with header as "default"
#    And with swagger schema validation path as ""

  Scenario: Client is able to hit the "/pet" endpoint to register his pet using http "post" verb. (Example: Request as JSON in feature.)
    Given with request end point as "/pet"
    And with request body json as
      """
            {
              "id": 1,
              "category": {
                        "id": 1,
                      "name": "bull_dog"
               },
              "name": "bull dog",
              "photoUrls": ["url_123", "url_2"],
              "tags": [
                  {
                        "id": 1,
                      "name": "danny"
                  }
                 ],
              "status": "available"
            }

      """
    When with method as "post"
    Then status as 200

  Scenario: Client is able to hit the "/pet" endpoint to register his pet using http "post" verb. (Example: Request built in the code)
    Given with request end point as "/pet"
    And with request body with default request builder for "registerPet"
    When with method as "post"
    Then status as 200
    And response contains string as ""
    And response contains key value pair as ""

# Other Usefull Steps
#    And response schema as "petSchema"
#    And response contains string as ""
#    And response contains key value pair as ""
#    And response contains data structure as ""


