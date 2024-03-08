package com.inchcape.inchcapeisthebest.controllers;

import com.inchcape.inchcapeisthebest.DTO.ActorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class WebController {

    private final RestTemplate restTemplate;

    @Autowired
    public WebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String home(Model model) {

        ResponseEntity<ActorDTO[]> response = restTemplate.getForEntity("http://localhost:8080/allActors", ActorDTO[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ActorDTO[] actors = response.getBody();
            model.addAttribute("actors", actors);
        } else {
            model.addAttribute("actors", new ActorDTO[0]);
        }

        return "home.html";
    }

}
