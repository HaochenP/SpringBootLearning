Feature: Actor


  Scenario: Actor is fetched by id
    Given the actor with id 1 exists
    When get request is made /actor/1
    Then an actor is returned