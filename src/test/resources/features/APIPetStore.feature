Feature: API Pet Store Feature

  Background: API Authorization
    Given with authorization mechanism as "na"
    And with header as "default"
#    Given with authorization mechanism as "outh2"
#    And with header as "key1=val1,key2=val2"


  @api
  Scenario: API pet store
    Given with request end point as "/pet"
    And with request body as "pet"
    When with method as "post"
    Then status as 200
#    And response schema as "petSchema"
#    And response contains string as ""
#    And response contains key value pair as ""
#    And response contains data structure as ""


