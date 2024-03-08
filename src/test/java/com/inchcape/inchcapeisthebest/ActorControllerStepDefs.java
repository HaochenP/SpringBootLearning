package com.inchcape.inchcapeisthebest;

import com.inchcape.inchcapeisthebest.controllers.ExampleController;
import com.inchcape.inchcapeisthebest.entities.Actor;
import com.inchcape.inchcapeisthebest.service.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class ActorControllerStepDefs {
    private ActorService actorService;
    private final Long expectedId = 1L;
    private final Actor actor = new Actor(expectedId, "John", "Doe", LocalDateTime.now(), new ArrayList<>());
    private Actor actualActor;
    @Before
    public void setUp(){
        actorService = mock(ActorService.class);
    }
    @Given("the actor with id {long} exists")
    public void givenActorOneExists(Long id){
          doReturn(actor).when(actorService).getActorById(id);
    }

    @When("get request is made /actor/{long}")
    public void whenRequestWithId(Long id){
        final ExampleController actorController = new ExampleController(actorService);
        try {
            actualActor = (Actor) actorController.getActorById(id).getBody();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Then("an actor is returned")
    public void thenActorIsReturned(){
        assertNotNull(actualActor);
        assertEquals("John", actualActor.getFirstname());
        assertEquals("Doe", actualActor.getLastName());
    }
}
