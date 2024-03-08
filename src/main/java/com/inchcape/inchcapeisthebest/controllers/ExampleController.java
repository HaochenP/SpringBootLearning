package com.inchcape.inchcapeisthebest.controllers;

import com.inchcape.inchcapeisthebest.DTO.ActorDTO;
import com.inchcape.inchcapeisthebest.entities.Actor;
import com.inchcape.inchcapeisthebest.entities.Film;
import com.inchcape.inchcapeisthebest.input.ActorInput;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.inchcape.inchcapeisthebest.service.ActorService;

import java.util.List;
import java.util.Optional;

@RestController
public class ExampleController {
    private final ActorService actorService;

    public ExampleController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("allActors")
    public ResponseEntity<?> getAllActors() {
        try{
            List<ActorDTO> actors = actorService.getActors();
            return ResponseEntity.ok(actors);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/addActor")
    public ResponseEntity<?> addActor(@Validated @RequestBody ActorInput data) {
        try{
            Actor actor = new Actor();
            actor.setFirstname(data.getFirstname());
            actor.setLastName(data.getLastName());
            return ResponseEntity.ok(actorService.saveActor(actor));
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while saving actor" + e.getMessage());
        }

    }

    @GetMapping("actorById/{id}")
    public ResponseEntity<?> getActorById(@PathVariable Long id) {
        try{
            ActorDTO actor = actorService.getActorById(id);
            if (actor == null) {
                return ResponseEntity.badRequest().body("Actor not found");
            }
            return ResponseEntity.ok(actor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while fetching actor" + e.getMessage());
        }
    }

    @PostMapping("saveActor")
    public Actor saveActor(Actor actor) {
        return actorService.saveActor(actor);
    }

    @DeleteMapping ("deleteActor/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable Long id)
    {
        try {
            ActorDTO actor = actorService.getActorById(id);
            if (actor == null) {
                return ResponseEntity.badRequest().body("Actor not found");
            }
            actorService.deleteActor(id);
            return ResponseEntity.ok("Actor deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while deleting actor" + e.getMessage());
        }
    }

    @PatchMapping("updateActor/{id}")
    public ResponseEntity<?> updateActor(@RequestBody ActorInput newActor, @PathVariable Long id) {
        try {
            Actor updatedActor = actorService.updateActor(id, newActor);
            if (updatedActor == null) {
                return ResponseEntity.badRequest().body("Actor not found");
            }
            return ResponseEntity.ok(actorService.updateActor(id, newActor));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/starsIn/{id}")
    public List<Film> starsIn(@PathVariable Long id) {
        final var actor = actorService.getActorById(id);
        return actor.getFilms();
    }

    @PostMapping("/addFilmToActor/{actorId}")
    public ResponseEntity<?> addFilmToActor(@PathVariable Long actorId, @RequestBody ActorDTO actorInput) {
          try{

              Actor actor = actorService.AddFilmToActor(actorId, actorInput);
              if(actor == null){
                  return ResponseEntity.badRequest().body("Actor not found");
              }
                return ResponseEntity.ok(actor);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while adding film to actor" + e.getMessage());
        }
    }

    @PostMapping("/deleteActorsByIds")
    public ResponseEntity<?> deleteActorsByIds(@RequestBody List<Long> ids) {
        try {
            actorService.deleteActorsByIds(ids);
            return ResponseEntity.ok("Actors deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while deleting actors" + e.getMessage());
        }
    }

    @GetMapping("/lastActor")
    public ResponseEntity<?> getLastActor() {
        try {
            Optional<Actor> actor = actorService.findLastActor();
            if (actor.isEmpty()) {
                return ResponseEntity.badRequest().body("Actor not found");
            }
            return ResponseEntity.ok(actor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while fetching last actor" + e.getMessage());
        }
    }


}

