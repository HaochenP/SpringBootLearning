Feature: Actor


  Scenario: Actor is fetched by id
    Given the actor with id 1 exists
    When a GET request is made for an actor with ID 1
    Then an actor is returned