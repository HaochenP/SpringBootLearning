package com.inchcape.inchcapeisthebest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CucumberContextConfiguration
public class SampleSteps {

    @Given("I have a configured Cucumber test")
    public void i_have_a_configured_cucumber_test() {
        // Setup code here
    }

    @When("I run the test")
    public void i_run_the_test() {
        // Action code here
    }

    @Then("I should see the test passing")
    public void i_should_see_the_test_passing() {
        // Assertion code here
    }
}
